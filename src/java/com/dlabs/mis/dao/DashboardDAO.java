/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlabs.mis.dao;

import com.dlabs.mis.controller.PermissionController;
import com.dlabs.mis.model.Permission;
import com.dlabs.mis.model.PermissionGroup;
import com.kjava.base.ReadableException;
import com.kjava.base.db.DaoUtil;
import com.kjava.base.util.JSONUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kamlesh the admin
 */
@Repository
public class DashboardDAO {
    @Autowired
    JSONUtil jsonUtil;
    @Autowired
    Properties sqlQueries;
    public List<Permission> getDashboardLink(Connection conn,int roleId, String groupId) throws ReadableException, SQLException {
        PermissionGroup group = new PermissionController().getAccessGroups().get(groupId);
        List<Permission> perm = group.getPermissions();
        List<Permission> result = new ArrayList<Permission>();
        ResultSet rs = DaoUtil.executeQuery(conn, sqlQueries.getProperty("DASHBOARD_LINKS"), new Object[]{roleId,groupId});
        if(rs.next()){
            int readonly = rs.getInt("readonly");
            for(int i=0;i<perm.size();i++){
                Permission p = perm.get(i);
                if((readonly & (int)Math.pow(2,p.getId()))!=0)
                     result.add(perm.get(i));
            }
        }
        return result;
    }
    
}
