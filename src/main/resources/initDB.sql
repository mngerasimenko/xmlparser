--DROP TABLE department IF EXISTS;

CREATE TABLE IF NOT EXISTS department
(
    id               INTEGER IDENTITY        NOT NULL,
    dep_code          VARCHAR(20)             NOT NULL,
    dep_job           VARCHAR(100)            NOT NULL,
    description      VARCHAR(255)            NOT NULL,
    PRIMARY KEY (ID)
);

CREATE UNIQUE INDEX IF NOT EXISTS department_unique_dep_code_dep_job
    ON department (dep_code, dep_job);

