import React, { useState, useEffect, useParams, forwardRef } from "react";
import { useHistory } from "react-router-dom";
import { useSelector } from "react-redux";
import {
  Grid,
  // Card,
  // CardContent,
  // Typography,
  // ButtonBase,
  // Modal,
  // Button,
} from "@material-ui/core";
import MaterialTable from "material-table";
import {
  AddBox,
  ArrowDownward,
  Check,
  ChevronLeft,
  ChevronRight,
  Clear,
  DeleteOutline,
  Edit,
  FilterList,
  FirstPage,
  LastPage,
  Remove,
  SaveAlt,
  Search,
  ViewColumn,
} from "@material-ui/icons";
// import { makeStyles } from "@material-ui/core/styles";
import { Alert } from "@material-ui/lab";
import { projectAxios, appAxios } from "../../utils/axios";

const tableIcons = {
  Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
  Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
  Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
  DetailPanel: forwardRef((props, ref) => (
    <ChevronRight {...props} ref={ref} />
  )),
  Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
  Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
  Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
  FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
  LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
  NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
  PreviousPage: forwardRef((props, ref) => (
    <ChevronLeft {...props} ref={ref} />
  )),
  ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
  SortArrow: forwardRef((props, ref) => <ArrowDownward {...props} ref={ref} />),
  ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
  ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />),
};

export default function Project() {
  var columns = [
    {
      title: "ID",
      field: "id",
      editable: "never",
      cellStyle: {
        width: "5%",
        maxWidth: "5%",
      },
    },
    {
      title: "Title",
      field: "title",
    },
    {
      title: "Descrption",
      field: "description",
    },
  ];
  const [projectList, setProjectList] = useState();
  const user = useSelector((state) => state.user);
  const [iserror, setIserror] = useState(false);
  const [errorMessages, setErrorMessages] = useState([]);

  async function addRow(data) {
    const response = await projectAxios.postProject(user.info.department.id, {
      title: data.title,
      description: data.description,
    });
    let dataAdd = [...projectList];
    dataAdd.push(response);
    setProjectList(dataAdd);
  }

  async function updateRow(data) {
    const response = await projectAxios.putProject(data.id, {
      title: data.title,
      description: data.description,
    });
    let dataUpdate = [...projectList];
    dataUpdate.map((du) => {
      if (du.id == data.id) {
        du.title = data.title;
        du.description = data.description;
      }
      return du;
    });
    setProjectList(dataUpdate);
  }

  async function deleteRow(data) {
    const response = await projectAxios.deleteProject(data.id);
    let dataDelete = [...projectList];
    setProjectList(dataDelete.filter((dd) => dd.id !== data.id));
  }

  const history = useHistory();

  useEffect(async () => {
    try {
      const responseData = await projectAxios.getAll();
      setProjectList(responseData);
      console.log(responseData);
    } catch (error) {
      console.error(error);
    }
    return () => {};
  }, []);

  const handleRowAdd = (newData, resolve) => {
    let errorList = [];
    if (!newData.title) {
      errorList.push("Please enter title");
    }
    if (!newData.description) {
      errorList.push("Please enter description");
    }

    if (errorList.length < 1) {
      addRow(newData);
      resolve();
    } else {
      setErrorMessages(errorList);
      setIserror(true);
      resolve();
    }
  };

  const handleRowUpdate = (newData, oldData, resolve) => {
    let errorList = [];
    if (!newData.title) {
      errorList.push("Please enter title");
    }
    if (!newData.description) {
      errorList.push("Please enter description");
    }

    if (errorList.length < 1) {
      updateRow(newData);
      resolve();
    } else {
      setErrorMessages(errorList);
      setIserror(true);
      resolve();
    }
  };

  const handleRowDelete = (oldData, resolve) => {
    deleteRow(oldData);
    resolve();
  };

  return (
    <>
      <div>
        <Grid container spacing={0}>
          <Grid item xs={1}></Grid>
          <Grid item xs={10}>
            <div>
              {iserror && (
                <Alert severity="error">
                  {errorMessages.map((msg, i) => {
                    return <div key={i}>{msg}</div>;
                  })}
                </Alert>
              )}
            </div>
            <MaterialTable
              title="Project List"
              columns={columns}
              data={projectList}
              icons={tableIcons}
              editable={
                user.info.role == "admin"
                  ? {
                      onRowAdd: (newData) =>
                        new Promise((resolve) => {
                          handleRowAdd(newData, resolve);
                        }),
                      onRowUpdate: (newData, oldData) =>
                        new Promise((resolve) => {
                          handleRowUpdate(newData, oldData, resolve);
                        }),
                      onRowDelete: (oldData) =>
                        new Promise((resolve) => {
                          handleRowDelete(oldData, resolve);
                        }),
                    }
                  : {
                      onRowAdd: (newData) =>
                        new Promise((resolve) => {
                          handleRowAdd(newData, resolve);
                        }),
                    }
              }
              onRowClick={(evt, selectedRow) =>
                history.push("/project/" + selectedRow.id)
              }
              options={{
                actionsColumnIndex: -1,
                minBodyHeight: "75vh",
                maxBodyHeight: "75vh",
                pageSize: 10,
                pageSizeOptions: [10, 20, 30],
              }}
            />
          </Grid>
          <Grid item xs={3}></Grid>
        </Grid>
      </div>
    </>
  );
}
