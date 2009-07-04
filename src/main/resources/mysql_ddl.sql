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

CREATE TABLE sequence (
  seq_name varchar(50) NOT NULL,
  seq_count decimal(38,0) default NULL,
  PRIMARY KEY  (seq_name)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO sequence (seq_name, seq_count) VALUES ("SEQ_GEN", 0);