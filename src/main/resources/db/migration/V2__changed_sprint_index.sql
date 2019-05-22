drop index if exists sprint_sprintid_uindex;

create unique index if not exists sprint_project_sprintid_uindex
    on sprint (sprintid, projectid);

alter table sprint
    add column effective_factor double precision,
    add column effective_factor_hist double precision;
