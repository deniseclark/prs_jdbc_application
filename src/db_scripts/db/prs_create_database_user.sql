-- Create a User and Grant Privs to that User
GRANT SELECT, INSERT, DELETE, UPDATE 
ON prs.*
TO prs_user@localhost IDENTIFIED BY 'prs_user';