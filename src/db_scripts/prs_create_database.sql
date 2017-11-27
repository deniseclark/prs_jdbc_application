-- *****************************************************************************
-- ***                                                                       ***
-- *** File Name:    create_database_prs.sql                                 ***
-- ***                                                                       ***
-- *** Project:      CapstoneProject (Java Bootcamp)                         ***
-- ***                                                                       ***
-- *** Written By:   Denise Clark                                            ***
-- ***                                                                       ***
-- *** Date Written: 01 November 2017                                        ***
-- ***                                                                       ***
-- *** Description:  This SQL Script will Create the mySQySQL Database "prs" ***
-- ***               (Purchase Request System) for the Java Bootcamp         ***
-- ***                Capstone Project.                                      ***
-- ***                                                                       ***
-- *** Database:     PRS                                                     ***
-- ***                                                                       ***
-- *** Tables:       USER                                                    ***
-- ***               STATUS                                                  ***
-- ***               VENDOR                                                  ***
-- ***               PURCHASE_REQUEST                                        ***
-- ***               PRODUCTS                                                ***
-- ***               PURCHASE_REQUEST_LINE_ITEM                              ***
-- ***                                                                       ***
-- *****************************************************************************

 --  DROP DATABASE IF EXISTS prs;
 --  CREATE DATABASE prs;
 --  USE prs;

     DROP TABLE IF EXISTS prs.purchase_request_line_item;
     DROP TABLE IF EXISTS prs.product;
     DROP TABLE IF EXISTS prs.purchase_request;
     DROP TABLE IF EXISTS prs.vendor;
     DROP TABLE IF EXISTS prs.status;
     DROP TABLE IF EXISTS prs.user;


   -- *********************************************
   -- *** Create Table: USER                    ***
   -- *********************************************
      CREATE TABLE prs.user 
        (ID                  INT(10)       NOT NULL  AUTO_INCREMENT,
         UserName            VARCHAR(20)   NOT NULL,
         Password            VARCHAR(10)   NOT NULL,
         FirstName           VARCHAR(20)   NOT NULL,
         LastName            VARCHAR(20)   NOT NULL,
         Phone               VARCHAR(12)   NOT NULL,
         Email               VARCHAR(75)   NOT NULL,
         IsReviewer          TINYINT(1)    NOT NULL,
         IsAdmin             TINYINT(1)    NOT NULL,
         IsActive            TINYINT(1)    DEFAULT 1 NOT NULL,
         DateCreated         DATETIME      DEFAULT CURRENT_TIMESTAMP NOT NULL,
         DateUpdated         DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
         UpdatedByUser       INT(10)       DEFAULT 1 NOT NULL,
      PRIMARY KEY user_id_pk (id),
      UNIQUE KEY user_uname_uk (UserName),
      UNIQUE KEY user_fname_uk (LastName, FirstName),
      UNIQUE KEY user_email_uk(Email));
 
 
   -- **************************************************
   -- *** Create Table: STATUS                       ***
   -- **************************************************
      CREATE TABLE prs.status
        (ID                  INT(10)       NOT NULL AUTO_INCREMENT,
         Description         VARCHAR(20)   NOT NULL,
         IsActive            TINYINT(1)    DEFAULT 1 NOT NULL,
         DateCreated         DATETIME      DEFAULT CURRENT_TIMESTAMP NOT NULL,
         DateUpdated         DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
         UpdatedByUser       INT(10)       DEFAULT 1 NOT NULL,
      PRIMARY KEY status_id_pk (ID),
      UNIQUE KEY status_desc_uk(Description));


   -- ****************************************************
   -- *** Create Table: VENDOR                         ***
   -- ****************************************************
      CREATE TABLE prs.vendor
        (ID                  INT(10)       NOT NULL AUTO_INCREMENT,
         Code                VARCHAR(10)   NOT NULL,
         Name                VARCHAR(255)  NOT NULL,
         Address             VARCHAR(255)  NOT NULL,
         City                VARCHAR(255)  NOT NULL,
         State               VARCHAR(2)    NOT NULL,
         Zip                 VARCHAR(5)    NOT NULL,
         Phone               VARCHAR(12)   NOT NULL,
         Email               VARCHAR(100)  NOT NULL,
         IsPreApproved       TINYINT(1)    NOT NULL,
         IsActive            TINYINT(1)    DEFAULT 1 NOT NULL,
         DateCreated         DATETIME      DEFAULT CURRENT_TIMESTAMP NOT NULL,
         DateUpdated         DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
         UpdatedByUser       INT(10)       DEFAULT 1 NOT NULL,
     PRIMARY KEY vendor_id_pk (ID),
     UNIQUE KEY vendor_code_uk (Code));


   -- *****************************************************
   -- *** Create Table: PRODUCT                         ***
   -- *****************************************************
      CREATE TABLE prs.product
        (ID                  INT(10)       NOT NULL AUTO_INCREMENT,
         VendorID            INT(10)       NOT NULL,
         PartNumber          VARCHAR(50)   NOT NULL,
         Name                VARCHAR(150)  NOT NULL,
         Price               DECIMAL(10,2) NOT NULL,
         Unit                VARCHAR(255)  NOT NULL,
         PhotoPath           VARCHAR(255),
         IsActive            TINYINT(1)    DEFAULT 1 NOT NULL,
         DateCreated         DATETIME      DEFAULT CURRENT_TIMESTAMP NOT NULL,
         DateUpdated         DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
         UpdatedByUser       INT(10)       DEFAULT 1 NOT NULL,
      PRIMARY KEY  product_pk (ID),
         FOREIGN KEY product_vendor_fk (VendorID) REFERENCES vendor(ID),
         UNIQUE KEY product_part_uk (VendorID, PartNumber));


   -- ****************************************************
   -- *** Create Table: PURCHASE_REQUEST               ***
   -- ****************************************************
      CREATE TABLE prs.purchase_request
        (ID                  INT(10)       NOT NULL AUTO_INCREMENT,
         UserID              INT(10)       NOT NULL,
         Description         VARCHAR(100)  NOT NULL,
         Justification       VARCHAR(255)  NOT NULL,
         DateNeeded          DATE,
         DeliveryMode        VARCHAR(25),
         StatusID            INT(10)       NOT NULL,
         Total               DECIMAL(10,2) NOT NULL,
         SubmittedDate       DATE          NOT NULL, 
         ReasonForRejection  VARCHAR(100),
         IsActive            TINYINT(1)    DEFAULT 1 NOT NULL,
         DateCreated         DATETIME      DEFAULT CURRENT_TIMESTAMP NOT NULL,
         DateUpdated         DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
         UpdatedByUser       INT(10)       DEFAULT 1 NOT NULL,
      PRIMARY KEY purchase_request_pk (ID),
      FOREIGN KEY purchase_request_user_fk (UserID) REFERENCES user(ID),
      FOREIGN KEY purchase_request_status_fk (StatusID) REFERENCES status(ID));


   -- *************************************************
   -- *** Create Table: PURCHASE_REQUEST_LINE_ITEM  ***
   -- *************************************************
      CREATE TABLE prs.purchase_request_line_item
        (ID                  INT(10)       NOT NULL AUTO_INCREMENT,
         PurchaseRequestID   INT(10)       NOT NULL,
         ProductID           INT(10)       NOT NULL,
         Quantity            INT(10)       NOT NULL,      
         IsActive            TINYINT(1)    DEFAULT 1 NOT NULL,
         DateCreated         DATETIME      DEFAULT CURRENT_TIMESTAMP NOT NULL,
         DateUpdated         DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
         UpdatedByUser       INT(10)       DEFAULT 1 NOT NULL,
      PRIMARY KEY purchase_req_line_item_pk (ID),
      FOREIGN KEY purchase_req_line_item_product_fk (ProductID) REFERENCES product(ID),
      FOREIGN KEY purchase_req_line_item_pr_fk (PurchaseRequestID) REFERENCES purchase_request(ID),
      UNIQUE KEY  purchase_req_line_item_uk (PurchaseRequestID, ProductID));

