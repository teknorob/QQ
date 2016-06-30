-- -----------------------------------------------------
-- Table role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS role (
  id_role INT NOT NULL AUTO_INCREMENT,
  cd_role VARCHAR(45) NOT NULL,
  PRIMARY KEY (id_role),
  UNIQUE INDEX cd_role_UNIQUE (cd_role ASC));



-- -----------------------------------------------------
-- Table user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
  id_user INT NOT NULL AUTO_INCREMENT,
  nm_user VARCHAR(100) NOT NULL,
  id_google VARCHAR(45) NOT NULL,
  id_role INT NOT NULL,
  PRIMARY KEY (id_user),
  UNIQUE INDEX id_google_UNIQUE (id_google ASC),
  INDEX fk_user_role1_idx (id_role ASC),
  CONSTRAINT fk_user_role1
    FOREIGN KEY (id_role)
    REFERENCES role (id_role)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION );


-- -----------------------------------------------------
-- Table god
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS god (
  id_god INT NOT NULL AUTO_INCREMENT,
  id_google VARCHAR(45) NOT NULL );

-- -----------------------------------------------------
-- Table db_info
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS db_info (
  cd_parameter VARCHAR( 255 ) PRIMARY KEY,
  tx_parameter VARCHAR( 255 ) NOT NULL);

-- -----------------------------------------------------
-- Set DB_INFO version number
-- -----------------------------------------------------
INSERT INTO db_info (cd_parameter,tx_parameter) VALUES ('VERSION','0.0.1');

-- -----------------------------------------------------
-- Set gods 
-- -----------------------------------------------------
INSERT INTO god (id_google) VALUES ('asdf');


-- -----------------------------------------------------
-- Set available team types
-- -----------------------------------------------------
INSERT INTO role (cd_role) VALUES ('GOD');
INSERT INTO role (cd_role) VALUES ('ADMIN');
INSERT INTO role (cd_role) VALUES ('USER');
INSERT INTO role (cd_role) VALUES ('SERVICE');
