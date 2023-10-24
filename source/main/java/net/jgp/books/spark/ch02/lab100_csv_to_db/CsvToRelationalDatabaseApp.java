package net.jgp.books.spark.ch02.lab100_csv_to_db;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

import static org.apache.spark.sql.functions.concat;
import static org.apache.spark.sql.functions.lit;

/**
 * CSV to a relational database.
 * <p><p/>
 * Your application, or driver, connects to a Spark cluster. From there, the application
 * tells the cluster what to do: the application drives the cluster.
 * In this scenario, the master starts by loading a CSV file and finishes by saving in a database.
 *
 * @author jgp
 */
public class CsvToRelationalDatabaseApp {

    /**
     * main() is your entry point to the application.
     *
     * @param args args
     */
    public static void main(String[] args) {
        CsvToRelationalDatabaseApp app = new CsvToRelationalDatabaseApp();
        app.start();
    }

    /**
     * The processing code.
     * <ol>
     *     <li>The whole dataset never hits our application (driver). The dataset is split
     *      between the partitions on the workers, not on the driver.</li>
     *      <li>The entire processing takes place in the workers.</li>
     *      <li>The workers save the data in their partition to the database.</li>
     *      <li>A fine-tuned database server will refuse too many connections, which will require more control in the application.<li/>
     * </ol>
     */
    private void start() {
        // Creates a session on a local master
        SparkSession spark = SparkSession.builder()
                .appName("CSV to DB")
                .master("local")
                //.master("spark://spark-master-146:7077")
                .getOrCreate();

        // Step 1: Ingestion
        // ---------

        // Reads a CSV file with header, called authors.csv, stores it in a
        // dataframe
        Dataset<Row> df = spark.read()
                .format("csv")
                .option("header", "true")
                .load("data/authors.csv");

        // Step 2: Transform
        // ---------

        // Creates a new column called "name" as the concatenation of lname, a
        // virtual column containing ", " and the fname column
        df = df.withColumn(
                "name",
                concat(df.col("lname"), lit("·"), df.col("fname")));

        // Step 3: Save
        // ---------

        // The connection URL, assuming your PostgreSQL instance runs locally on
        // the
        // default port, and the database we use is "spark_labs"
        String dbConnectionUrl = "jdbc:mysql://172.22.105.146:3306/flow_analyze";

        // Properties to connect to the database, the JDBC driver is part of our
        // pom.xml
        Properties prop = new Properties();
        prop.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("user", "root");
        prop.setProperty("password", "rootpass");

        // Write in a table called ch02
        df.write()
                .mode(SaveMode.Overwrite)
                .jdbc(dbConnectionUrl, "ch02", prop);

        System.out.println("Process complete");
    }
}