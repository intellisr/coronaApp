module.exports = app => {
  const dailyReport = require("../controllers/dailyReport.controller.js");

  var router = require("express").Router();

  // Create a new Tutorial
  router.post("/addDailyReport", dailyReport.create);

  // Retrieve Todays Report
  router.get("/getDailyReport:date", dailyReport.findOne);


  // Delete a Tutorial with id
  router.delete("/deleteDailyReport:date", dailyReport.delete);

  app.use("/api/covid", router);
};
