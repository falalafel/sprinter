alter table project
  add column starting_factor double precision;

alter table sprint
  add column factor double precision,
  add column enddate date,
  add column original_estimated_hours integer,
  add column end_planned_hours integer,
  add column burned_hours integer;

update project
  set starting_factor = 2.5
  where starting_factor is null;

update sprint
  set factor = 2.5
  where factor is null;

update sprint
  set enddate = '2077-04-05'
  where enddate is null;

alter table project
  alter column starting_factor set not null;

alter table sprint
  alter column factor set not null,
  alter column enddate set not null;
