DROP TABLE IF EXISTS junctions CASCADE;
DROP TABLE IF EXISTS roads CASCADE;
DROP TABLE IF EXISTS points_of_interest;

CREATE TABLE junctions (
	id INTEGER PRIMARY KEY,
	name VARCHAR(64) CHECK (name <> ''),
	x FLOAT,
	y FLOAT
);

CREATE INDEX junx_ind_name ON junctions(name);
CREATE INDEX junx_ind_x ON junctions(x);
CREATE INDEX junx_ind_y ON junctions(y);

CREATE TABLE roads (
	id INTEGER PRIMARY KEY,
	name varchar(64) NOT NULL CHECK (name <> ''),
	start_junction INTEGER NOT NULL,
	end_junction INTEGER NOT NULL CHECK (start_junction <> end_junction),
	one_way BOOLEAN NOT NULL,
	pass_time FLOAT NOT NULL CHECK (pass_time > 0.0),

	CONSTRAINT fk_start FOREIGN KEY (start_junction) 
		REFERENCES junctions(id) 
			ON UPDATE CASCADE 
			ON DELETE CASCADE,
	CONSTRAINT fk_end FOREIGN KEY (end_junction)
		REFERENCES junctions(id)
			ON UPDATE CASCADE 
			ON DELETE CASCADE
);

CREATE INDEX roads_ind_name ON roads(name);

CREATE TABLE points_of_interest (
	id INTEGER PRIMARY KEY,
	name VARCHAR(64) NOT NULL CHECK (name <> ''),
	road INTEGER NOT NULL,
	x FLOAT NOT NULL,
	y FLOAT NOT NULL,
	position FLOAT NOT NULL CHECK (position >= 0.0 AND position <= 1.0),

	CONSTRAINT fk_road FOREIGN KEY (road)
		REFERENCES roads(id)
			ON UPDATE CASCADE
			ON DELETE CASCADE
);

CREATE INDEX poi_ind_name ON points_of_interest(name);
CREATE INDEX poi_ind_x ON points_of_interest(x);
CREATE INDEX poi_ind_y ON points_of_interest(y);
		
