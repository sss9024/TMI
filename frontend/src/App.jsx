import { Container } from "@material-ui/core";
import React, { useState } from "react";
import { Route, Redirect } from "react-router-dom";
import Navigation from "./components/Navigation";
import About from "./pages/About";
import Login from "./pages/login/Login";
import Project from "./pages/project/Project";
import ProjectApp from "./pages/project/ProjectApp";
import Plugin from "./components/about/Plugin";
import "./App.css";
import { useDispatch, useSelector } from "react-redux";
import { LOG_OUT } from "./redux/user";

function App() {
  const user = useSelector((state) => state.user);
  const dispatch = useDispatch();
  const logout = () => dispatch(LOG_OUT());

  console.log(user);

  return (
    <>
      {user.isLoggedIn ? (
        <>
          <Redirect to="/project" />
          <Navigation logout={logout} />
          <Container
            style={{
              backgroundColor: "white",
              padding: 20,
              minHeight: "100vh",
              // maxWidth: "75%",
            }}
          >
            <div style={{ height: 64 }} />
            <Route path="/project" exact component={Project} />
            <Route path="/project/:id" component={ProjectApp} />
            <Route path="/plugin" component={Plugin} />
            <Route path="/about" component={About} />
          </Container>
        </>
      ) : (
        <Route path="/" component={Login} />
      )}
    </>
  );
}

export default App;
