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
  nm_user VARCHAR(45) NOT NULL,
  id_google VARCHAR(45),
  ur_avatar VARCHAR(600),
  id_role INT NOT NULL,
  tx_phone_number VARCHAR(45),
  tx_email VARCHAR(150) NOT NULL,
  cd_notification_type VARCHAR(30) NOT NULL,
  PRIMARY KEY (id_user),
  UNIQUE INDEX nm_steam_UNIQUE (id_google ASC),
  INDEX fk_player_role1_idx (id_role ASC),
  CONSTRAINT fk_player_role1
    FOREIGN KEY (id_role)
    REFERENCES role (id_role)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table queue
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS queue (
  id_queue INT NOT NULL AUTO_INCREMENT,
  nm_queue VARCHAR(45) NOT NULL,
  no_ticket_expiry_duration INT NOT NULL,
  ti_open_time DATETIME NOT NULL,
  ti_close_time DATETIME NULL,
  PRIMARY KEY (id_queue));


-- -----------------------------------------------------
-- Table ticket
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ticket (
  id_ticket INT NOT NULL AUTO_INCREMENT,
  id_queue INT NOT NULL,
  id_user INT NOT NULL,
  dt_last_updated TIMESTAMP NOT NULL,
  fl_offender BOOLEAN NOT NULL,
  PRIMARY KEY (id_ticket),
  INDEX fk_ticket_queue1_idx (id_queue ASC),
  INDEX fk_ticket_user1_idx (id_user ASC),
  CONSTRAINT fk_ticket_queue1
    FOREIGN KEY (id_queue)
    REFERENCES queue (id_queue)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_ticket_user1
    FOREIGN KEY (id_user)
    REFERENCES user (id_user)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table administrator
--   This table is pre-propulated in the init script with 
--   google id's of pre-determined administrators. 
--   Intended for read-only access from application.
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS administrator (
  id_administrator INT NOT NULL,
  id_google VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_administrator));



-- -----------------------------------------------------
-- Table db_info
--   This table is pre-propulated in the init script with
--   the database version number.
--   Intended for read-only access from application.
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS db_info (
  cd_parameter VARCHAR( 255 ) PRIMARY KEY,
  tx_parameter VARCHAR( 255 ) NOT NULL);


-- ---PRESETS---
--

-- -----------------------------------------------------
-- Set DB_INFO version number
-- -----------------------------------------------------
INSERT INTO db_info (cd_parameter,tx_parameter) VALUES ('VERSION','0.0.1');

-- -----------------------------------------------------
-- Set administrators 
-- -----------------------------------------------------
INSERT INTO administrator (id_google) VALUES ('107407436085247891239');

-- -----------------------------------------------------
-- Set available role types
-- -----------------------------------------------------
INSERT INTO role (cd_role) VALUES ('ADMIN');
INSERT INTO role (cd_role) VALUES ('USER');
INSERT INTO role (cd_role) VALUES ('SERVICE');

-- -----------------------------------------------------
-- Set service account
-- -----------------------------------------------------
INSERT INTO user (
  nm_user,
  id_role,
  tx_phone_number )
SELECT DISTINCT 
  'QQ Service',
  id_role,
  '0000000000'
FROM role
WHERE cd_role='SERVICE'
  
