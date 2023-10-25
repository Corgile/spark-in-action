## ch01 summary

---

## ch02 summary

- Your application is the driver. Data may not have to come to the driver; it can be driven remotely. It is important to
  remember this when you size your deployment (see chapters 5, 6, and 18).
- The driver connects to a master and gets a session. Data will be attached to this session; the session defines the
  life cycle of the data on the worker’s nodes.
- The master can be local (your local machine) or a remote cluster. Using the local mode will not require you to build a
  cluster, making your life much easier while you are developing.
- Data is partitioned and processed within the partition. Partitions are in memory.
- Spark can easily read from CSV files (more details in chapter 7).
- Spark can easily save data in relational databases (more details in chapter 17).
- Spark is lazy: it will work only when you ask it to do so via an action. This laziness is good for you, and chapter 4
  provides more details.
- Spark’s APIs rely heavily on method chaining

## ch03 summary

- A dataframe is an immutably distributed collection of data, organized into
  named columns. Basically, a dataframe is an RDD with a schema.
- A dataframe is implemented as a dataset of rows—or in code: `Dataset<Row>`.
- A dataset is implemented as a dataset of anything except rows—or in code: `Dataset<String>`, `Dataset<Book>`, or
  `Dataset<SomePojo>`.
- Dataframes can store columnar information, like a CSV file, and nested fields
  and arrays, like a JSON file. Whether you are working with CSV files, JSON files,
  or other formats, the dataframe API remains the same.
- In a JSON document, you can access nested fields by using a dot (.).
- The API for the dataframe can be found at http://mng.bz/qXYE; see the reference section for details on how to use
  dataframes.
- The API for the static methods can be found at http://mng.bz/5AQD (and in
  appendix G); see the reference section for details on how to use static methods.
- If you do not care about column names when you union two dataframes, use `union()`.
- If you care about column names when you union two dataframes, use `unionByName()`.
- You can reuse your POJOs directly in a dataset in Spark.
- An object must be serializable if you want to have it as part of a dataset.
- The dataset’s `drop()` method removes a column in the dataframe.
- The dataset’s `col()` method returns a dataset’s column based on its name.
- The `to_date()` static function transforms a date as a string to a date.
- The `expr()` static function will compute the result of expressions by using field names.
- The `lit()` static function returns a column with a literal value.
- A resilient distributed dataset (RDD) is an immutably distributed collection of elements of your data.
- You should use a dataframe over an RDD when performance is critical.
- Tungsten storage relies on dataframes.
- Catalyst is the transformation optimizer (see chapter 4). It relies on dataframes to optimize actions and
  transformations.
- APIs across Spark libraries (graph, SQL, machine learning, or streaming) are becoming unified under the dataframe API