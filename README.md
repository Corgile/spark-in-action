## ch01 summary

---


## ch02 summary

- Your application is the driver. Data may not have to come to the driver; it can be driven remotely. It is important to remember this when you size your deployment (see chapters 5, 6, and 18).
- The driver connects to a master and gets a session. Data will be attached to this session; the session defines the life cycle of the data on the worker’s nodes.
- The master can be local (your local machine) or a remote cluster. Using the local mode will not require you to build a cluster, making your life much easier while you are developing.
- Data is partitioned and processed within the partition. Partitions are in memory.
- Spark can easily read from CSV files (more details in chapter 7).
- Spark can easily save data in relational databases (more details in chapter 17).
- Spark is lazy: it will work only when you ask it to do so via an action. This laziness is good for you, and chapter 4 provides more details.
- Spark’s APIs rely heavily on method chaining