CREATE DATABASE flickrvote;

CREATE TABLE challenge (
  id bigint(20) NOT NULL,
  tag varchar(50) NOT NULL,
  description varchar(255) NOT NULL,
  start_date date default NULL,
  voting_open_date date default NULL,
  end_date date default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE photographer (
  id bigint(20) NOT NULL,
  token varchar(100) default NULL,
  username varchar(50) default NULL,
  fullname varchar(100) default NULL,
  administrator tinyint(1) default NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE image (
  id bigint(20) NOT NULL,
  page varchar(255),
  medium_image VARCHAR(255),
  title varchar(100),
  photographer_id bigint(20),
  challenge_id bigint(29),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

ALTER TABLE image ADD CONSTRAINT FK_image_photographer_id FOREIGN KEY (photographer_id) REFERENCES photographer (id);
ALTER TABLE image ADD CONSTRAINT FK_image_challenge_id FOREIGN KEY (challenge_id) REFERENCES challenge (id);

CREATE TABLE sequence (
  seq_name varchar(50) NOT NULL,
  seq_count decimal(38,0) default NULL,
  PRIMARY KEY  (seq_name)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO sequence (seq_name, seq_count) VALUES ("SEQ_GEN", 0);

ALTER TABLE image DROP FOREIGN KEY FK_image_photographer_id;
ALTER TABLE image DROP FOREIGN KEY FK_image_challenge_id;
DROP TABLE image;
DROP TABLE challenge;
DROP TABLE photographer;
DELETE FROM sequence WHERE seq_name = 'SEQ_GEN';