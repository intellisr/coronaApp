const db = require("../models");
const DailyReport = db.dailyReport;

// Create and Save a new dailyReport
exports.create = (req, res) => {
  // Validate request
  if (!req.body.title) {
    res.status(400).send({ message: "Content can not be empty!" });
    return;
  }

  // Create a DailyReport
  const dailyReport = new DailyReport({
    update_date_time: req.body.update_date_time,
    local_new_cases: req.body.local_new_cases,
    local_total_cases: req.body.local_total_cases,
    local_deaths: req.body.local_deaths,
    local_new_deaths: req.body.local_new_deaths,
    local_recovered: req.body.local_recovered
  });

  // Save dailyReport in the database
  dailyReport
    .save(dailyReport)
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the DailyReport."
      });
    });
};


// Find a single DailyReport with an date
exports.findOne = (req, res) => {
  const date = req.params.date;

  DailyReport.find({ update_date_time: date })
    .then(data => {
      if (!data)
        res.status(404).send({ message: "Not found DailyReport with Date " + date });
      else res.send(data);
    })
    .catch(err => {
      res
        .status(500)
        .send({ message: "Error retrieving DailyReport with Date=" + date });
    });
};

// Delete a DailyReport with the specified id in the request
exports.delete = (req, res) => {
  const date = req.params.date;

  DailyReport.findByIdAndRemove(id, { update_date_time: date })
    .then(data => {
      if (!data) {
        res.status(404).send({
          message: `Cannot delete DailyReport with date=${date}. Maybe DailyReport was not found!`
        });
      } else {
        res.send({
          message: "DailyReport was deleted successfully!"
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Could not delete DailyReport with date=" + date
      });
    });
};


