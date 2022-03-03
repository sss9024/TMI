import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import ProjectAppCoverageGraphs from "../../components/graph/ProjectAppCoverageGraphs";
import { appAxios, projectAxios, reportAxios } from "../../utils/axios";
import {
  Box,
  Button,
  colors,
  makeStyles,
  Modal,
  Typography,
} from "@material-ui/core";
import CreateAppForm from "../../components/form/CreateAppForm";
import ProjectAppPassRateGraphs from "../../components/graph/ProjectAppPassRateGraphs";
import TotalDoughnutGraph from "../../components/graph/TotalDoughnutGraph";
import datetime from "../../utils/moment";
import {
  DataGrid,
  GridToolbarContainer,
  GridToolbarExport,
} from "@material-ui/data-grid";
import CoverageDetail from "../../components/coverage/CoverageDetail";

function getModalStyle() {
  const top = 50;
  const left = 50;

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`,
  };
}

const useStyles = makeStyles((theme) => ({
  createAppForm: {
    position: "absolute",
    width: 500,
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
  },
  testDetail: {
    position: "absolute",
    width: "90%",
    height: "90%",
    overflowY: "hidden",
    overflowX: "auto",
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
  },
  dataGrid: {
    cursor: "pointer",
    "&:hover": {
      cursor: "hand",
    },
  },
}));

const columns = [
  { field: "title", headerName: "App Name", flex: 1.2 },
  {
    field: "recentDatetime",
    headerName: "Recent Build Datetime",
    type: "date",
    flex: 1.2,
    renderCell: (params) => (
      <>
        {params.getValue("recentDatetime") && (
          <div>{datetime(params.getValue("recentDatetime"))}</div>
        )}
      </>
    ),
  },
  {
    field: "lineCov",
    headerName: "Line Cov.(%)",
    description: "Line Coverage (Covered / Missed)",
    type: "number",
    flex: 1,
    valueGetter: (params) => {
      const covered = params.getValue("recentTotalLineCovCovered");
      const missed = params.getValue("recentTotalLineCovMissed");
      const total = covered + missed;

      if (total) {
        return Math.round((covered / total) * 10000) / 100;
      }
      return;
    },
  },
  {
    field: "recentTotalBranchCov",
    headerName: "Branch Cov.(%)",
    type: "number",
    flex: 1,
    valueGetter: (params) => {
      const covered = params.getValue("recentTotalBranchCovCovered");
      const missed = params.getValue("recentTotalBranchCovMissed");
      const total = covered + missed;

      if (total) {
        return Math.round((covered / total) * 10000) / 100;
      }
      return;
    },
  },
  {
    field: "recentTotalPassRate",
    headerName: "Pass Rate(%)",
    type: "number",
    flex: 1,
    valueGetter: (params) => {
      const pass = params.getValue("recentTotalRunCount");
      const fail = params.getValue("recentTotalFailCount");
      const error = params.getValue("recentTotalErrorCount");
      const skip = params.getValue("recentTotalSkipCount");
      const total = pass + fail + error + skip;

      if (total) {
        return Math.round((pass / total) * 10000) / 100;
      }
      return;
    },
  },
];

// recent_datetime
// recent_totalBranchCovCovered
// recent_totalBranchCovMissed
// recent_totalElapsedTime
// recent_totalErrorCount
// recent_totalFailCount
// recent_totalLineCovCovered
// recent_totalLineCovMissed
// recent_totalRunCount
// recent_totalSkipCount

function CustomToolbar() {
  return (
    <GridToolbarContainer>
      <GridToolbarExport />
    </GridToolbarContainer>
  );
}

export default function ProjectApp() {
  const params = useParams();
  const [project, setProject] = useState({});
  const [appList, setAppList] = useState([]);
  const [modalCreateAppFromOpen, setModalCreateAppFormOpen] = useState(false);
  const [modalCoverageDetailOpen, setModalCoverageDetailOpen] = useState(false);
  const [coverageDetailData, setCoverageDetailData] = useState([]);
  const [selectedAppTitle, setSelectedAppTitle] = useState("");
  const [selectedAppId, setSelectedAppId] = useState("");
  const [selectionModel, setSelectionModel] = useState([]);
  const [selectedTotalLineCoverage, setSelectedTotalLineCoverage] = useState([
    0, 0,
  ]);
  const [selectedTotalBranchCoverage, setSelectedTotalBranchCoverage] =
    useState([0, 0]);
  const [selectedTotalPassRate, setSelectedTotalPassRate] = useState([
    0, 0, 0, 0,
  ]);

  const classes = useStyles();
  // getModalStyle is not a pure function, we roll the style only on the first render
  const [modalStyle] = useState(getModalStyle);

  const modalCreateAppFrom = (
    <div style={modalStyle} className={classes.createAppForm}>
      <CreateAppForm
        close={() => setModalCreateAppFormOpen(false)}
        appList={appList}
        setAppList={setAppList}
      />
    </div>
  );

  const modalCoverageDetail = (
    <div style={modalStyle} className={classes.testDetail}>
      <CoverageDetail
        close={() => setModalCoverageDetailOpen(false)}
        data={coverageDetailData}
        title={selectedAppTitle}
        aid={selectedAppId}
      />
    </div>
  );

  useEffect(async () => {
    try {
      const project = await projectAxios.getOne(params.id);
      setProject(project);
      console.log(project);
      const responseData = await appAxios.getAppByProjectId(params.id);
      setAppList(responseData);
      const appIdList = responseData.map((r) => r.id);
      setSelectionModel(appIdList);
      console.log(responseData);
    } catch (error) {
      console.error(error);
    }
    return () => {};
  }, []);
  return (
    <>
      <div style={{ marginBottom: 15 }}>
        <div style={{ display: "flex" }}>
          <Typography style={{ flexGrow: 1 }} variant="h4">
            {project.title}
          </Typography>
          <Button
            variant="contained"
            onClick={() => setModalCreateAppFormOpen(true)}
          >
            Create new app
          </Button>
        </div>

        <Typography style={{ fontSize: "20px", color: "#454545" }}>
          {project.description}
        </Typography>
        {/* <Typography variant="small">담당부서: {project.department}</Typography>
        <br></br>
        <Typography variant="small">
          등록일: {datetime(project.regDate)}{" "}
        </Typography>{" "} */}
      </div>
      <div
        style={{
          display: "flex",
          marginTop: "20px  ",
        }}
      >
        <div
          style={{
            display: "flex",
            justifyContent: "space-evenly",
            flexDirection: "column",
            marginRight: "40px",
          }}
        >
          <TotalDoughnutGraph
            data={selectedTotalLineCoverage}
            title="Line Cov."
          />
          <TotalDoughnutGraph
            data={selectedTotalBranchCoverage}
            title="Branch Cov."
          />
          <TotalDoughnutGraph data={selectedTotalPassRate} title="Pass Rate" />
        </div>
        <div style={{ flexGrow: 1, marginTop: "20px" }}>
          <div style={{ height: "75vh", width: "100%" }}>
            <DataGrid
              className={classes.dataGrid}
              components={{
                Toolbar: CustomToolbar,
              }}
              rows={appList}
              columns={columns}
              pageSize={5}
              checkboxSelection
              selectionModel={selectionModel}
              onSelectionModelChange={(newSelection) => {
                let newSelectedTotalLineCoverage = [0, 0];
                let newSelectedTotalBranchCoverage = [0, 0];
                let newSelectedTotalPassRate = [0, 0, 0, 0];
                appList.forEach((app) => {
                  if (newSelection.selectionModel.indexOf(app.id) !== -1) {
                    newSelectedTotalLineCoverage[0] +=
                      app.recentTotalLineCovCovered;
                    newSelectedTotalLineCoverage[1] +=
                      app.recentTotalLineCovMissed;
                    newSelectedTotalBranchCoverage[0] +=
                      app.recentTotalBranchCovCovered;
                    newSelectedTotalBranchCoverage[1] +=
                      app.recentTotalBranchCovMissed;
                    newSelectedTotalPassRate[0] += app.recentTotalRunCount;
                    newSelectedTotalPassRate[1] += app.recentTotalFailCount;
                    newSelectedTotalPassRate[2] += app.recentTotalErrorCount;
                    newSelectedTotalPassRate[3] += app.recentTotalSkipCount;
                  }
                });
                // no date exception
                // if (
                //   !newSelectedTotalLineCoverage[0] &&
                //   !newSelectedTotalLineCoverage[1]
                // )
                //   newSelectedTotalLineCoverage = [1, 0];
                // if (
                //   !newSelectedTotalBranchCoverage[0] &&
                //   !newSelectedTotalBranchCoverage[1]
                // )
                //   newSelectedTotalBranchCoverage = [1, 0];
                // if (
                //   !newSelectedTotalPassRate[0] &&
                //   !newSelectedTotalPassRate[1] &&
                //   !newSelectedTotalPassRate[2] &&
                //   !newSelectedTotalPassRate[3]
                // )
                //   newSelectedTotalPassRate = [1, 0, 0, 0];

                setSelectedTotalLineCoverage(newSelectedTotalLineCoverage);
                setSelectedTotalBranchCoverage(newSelectedTotalBranchCoverage);
                setSelectedTotalPassRate(newSelectedTotalPassRate);
              }}
              onCellClick={async (cell, event) => {
                if (cell.field !== "__check__") {
                  event.preventDefault();
                  event.stopPropagation();
                  setModalCoverageDetailOpen(true);
                  const responseData = await reportAxios.getListByAppId(
                    cell.row.id
                  );
                  setCoverageDetailData(responseData);
                  setSelectedAppTitle(cell.row.title);
                  setSelectedAppId(cell.row.id);
                }
              }}
            />
          </div>
        </div>
      </div>
      <Modal
        open={modalCreateAppFromOpen || modalCoverageDetailOpen}
        onClose={() => {
          setModalCreateAppFormOpen(false);
          setModalCoverageDetailOpen(false);
        }}
      >
        {modalCreateAppFromOpen ? (
          modalCreateAppFrom
        ) : modalCoverageDetailOpen ? (
          modalCoverageDetail
        ) : (
          <></>
        )}
      </Modal>
    </>
  );
}
