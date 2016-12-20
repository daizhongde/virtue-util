SELECT t1.AUDIT_LEVEL, 
       t1.AUDIT_ID, 
       t1.AUDIT_ITEM, 
       t1.FARES_DRYRUN_ID, 
       t1.DRYRUN_NAME, 
       t1.ENV, 
       t1.DOMAIN, 
       t1.CONFIG_AUTHOR, 
       t1.AUDIT_DATE, 
       t1.OK, 
       t1.INVALID_COUNT,
IF(ok='FALSE',IFNULL(t4.reason,''),'') REASON, IFNULL(t4.dmp_no,'') DMP_NO

 FROM 
(
	SELECT t2.audit_level "audit_level",
		   t2.AUDIT_ID "audit_id", 
	       t2.audit_name AS "audit_item",
	       t1.fares_dryrun_id "fares_dryrun_id",
	       (SELECT MIG_DRYRUN_NAME FROM tool.V_DRYRUN_CONFIG WHERE MIG_DRYRUN_ID=t1.FARES_DRYRUN_ID ) "dryrun_name",
	        -- (SELECT IFNULL(VALUE,'') FROM tool.V_DRYRUN_ENV WHERE CODE=t1.env ) "env",
	        IFNULL(t1.ENV,'') "env",
	       t2.domain "domain",
	       t2.AUDIT_AUTHOR AS "config_author",
	       DATE_FORMAT(t1.hdate,"%Y-%m-%d") "audit_date",
	-- acorss 0 clock audit have problem
	       IF(SUM(IF(t1.success_flag='0',1,IF(t1.result='0',1,0)))>0,'FALSE','TRUE') AS "ok",
	       CAST(IF(MIN(t1.success_flag)='0',-1,SUM(t1.min_value))  AS SIGNED) AS "invalid_count"
	  FROM tool.mig_auditv_result t1, tool.mig_auditv_config t2
	 WHERE t1.audit_id=t2.audit_id
	--    AND t1.fares_dryrun_id=7 -- AND t1.env='C' AND t1.domain='2' AND t2.AUDIT_AUTHOR='huyx3'
     GROUP BY env,domain, config_author, AUDIT_ID, audit_item,fares_dryrun_id,  audit_date
) t1
  LEFT OUTER JOIN tool.mig_auditv_errreason t4
    ON t1.audit_id = t4.audit_id AND t1.env=t4.env
  --  WHERE ok='false'