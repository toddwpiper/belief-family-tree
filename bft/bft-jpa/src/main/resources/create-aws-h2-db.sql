CREATE TABLE bft.BELIEF(
    CHANGE_SET_ID INTEGER NOT NULL IDENTITY,
    CREATED_TS TIMESTAMP,
    DESCRIPTION VARCHAR(200),
    PROCESSED_TS TIMESTAMP,
    COMMENT_TEXT VARCHAR(500),
    OPI_TYPE VARCHAR(10),
    CURRENT_MODULE_NAME VARCHAR(20),
    STATUS_CODE VARCHAR(20),
    BINARY_TRANSFER_TYPE VARCHAR(10),
    IP_RIGHT_TYPE_CODE VARCHAR(10),
    PARENT_CHANGE_SET_ID INTEGER,
    CHANGE_SET_TYPE_CODE VARCHAR(20),
    RANGE_CSV VARCHAR(1000)
);

CREATE TABLE bft.BELIEF(
	BELIEF_ID INTEGER NOT NULL IDENTITY
);

ALTER TABLE bft.BELIEF ADD CONSTRAINT PK_BELIEF PRIMARY KEY(BELIEF_ID);

GRANT SELECT, UPDATE, INSERT, DELETE ON bft.BELIEF TO GROUP bft;  


