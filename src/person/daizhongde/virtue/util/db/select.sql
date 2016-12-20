SELECT MAX(IF(create_btn='Y',1,0)) create_btn, MAX(IF(update_btn='Y',1,0)) update_btn, MAX(IF(update_btn1='Y',1,0)) update_btn1,
      MAX(IF(read_btn='Y',1,0)) read_btn,      MAX(IF(delete_btn='Y',1,0)) delete_btn, MAX(IF(delete_btn1='Y',1,0)) delete_btn1,
      MAX(IF(delete_btn2='Y',1,0)) delete_btn2,MAX(IF(import_btn='Y',1,0)) import_btn, MAX(IF(import_wizardbtn='Y',1,0)) import_wizardbtn
  FROM tool.`t_authority_rmrelation`
 WHERE n_mid=10055 AND n_rid IN
     ( SELECT n_rid FROM tool.`t_authority_urrelation` WHERE n_uid=122961 )