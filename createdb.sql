CREATE TABLE building (
bID varchar2(20) PRIMARY KEY,
bName varchar2(50),
num int,
shape SDO_GEOMETRY
);

CREATE TABLE photographer (
pgID varchar2(20) PRIMARY KEY,
shape SDO_GEOMETRY
);

CREATE TABLE photo (
pID varchar2(20) PRIMARY KEY,
pgID varchar2(20) REFERENCES photographer(pgID),
shape SDO_GEOMETRY
);

CREATE TABLE temp(
shape SDO_GEOMETRY
);

INSERT INTO user_sdo_geom_metadata
    (TABLE_NAME,
     COLUMN_NAME,
     DIMINFO,
     SRID)
  VALUES (
  'building',
  'shape',
  SDO_DIM_ARRAY(
    SDO_DIM_ELEMENT('X', 0, 820, 0.5), --maybe 0.005
    SDO_DIM_ELEMENT('Y', 0, 580, 0.5)
     ),
  NULL   -- SRID
);

INSERT INTO user_sdo_geom_metadata
    (TABLE_NAME,
     COLUMN_NAME,
     DIMINFO,
     SRID)
  VALUES (
  'photo',
  'shape',
  SDO_DIM_ARRAY(
    SDO_DIM_ELEMENT('X', 0, 820, 0.5), --maybe 0.005
    SDO_DIM_ELEMENT('Y', 0, 580, 0.5)
     ),
  NULL   -- SRID
);

INSERT INTO user_sdo_geom_metadata
    (TABLE_NAME,
     COLUMN_NAME,
     DIMINFO,
     SRID)
  VALUES (
  'photographer',
  'shape',
  SDO_DIM_ARRAY(
    SDO_DIM_ELEMENT('X', 0, 820, 0.5), --maybe 0.005
    SDO_DIM_ELEMENT('Y', 0, 580, 0.5)
     ),
  NULL   -- SRID
);

INSERT INTO user_sdo_geom_metadata
    (TABLE_NAME,
     COLUMN_NAME,
     DIMINFO,
     SRID)
  VALUES (
  'temp',
  'shape',
  SDO_DIM_ARRAY(
    SDO_DIM_ELEMENT('X', 0, 820, 0.5), --maybe 0.005
    SDO_DIM_ELEMENT('Y', 0, 580, 0.5)
     ),
  NULL   -- SRID
);

CREATE INDEX building_idx
   ON building(shape)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;
   
CREATE INDEX photo_idx
   ON photo(shape)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;
   
CREATE INDEX photographer_idx
   ON photographer(shape)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;
   
CREATE INDEX temp_idx
   ON temp(shape)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;


   
   