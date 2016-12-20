select a.*,b.job_ins_name, c.node_name
from 
(
select t1.LOG_ID "log_id", t1.JOB_INS_ID "job_ins_id", t1.task_id "task_id", t1.level "level",
 t1.LOG_MSG "log_msg", date_format(t1.ctime, '%Y-%m-%d %H:%i:%S') "ctime", 
 t1.REMArk "remark" 
 from tool.mig_job_log t1
  order by task_id asc
 ) a,
 MIG_JOB_INS b,
 mig_job_process c
where a.job_ins_id = b.job_ins_id
and a.job_ins_id = c.job_ins_id
and a.task_id = c.node_id