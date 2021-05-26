module.exports = mongoose => {
  var schema = mongoose.Schema(
    {
      update_date_time: Date,
      local_new_cases: Number,
      local_total_cases: Number,
      local_deaths: Number,
      local_new_deaths: Number,
      local_recovered: Number
    }
  );

  schema.method("toJSON", function() {
    const { __v, _id, ...object } = this.toObject();
    object.id = _id;
    return object;
  });

  const dailyReport = mongoose.model("dailyReport", schema);
  return dailyReport;
};
