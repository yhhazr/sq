/**
 * Copyright  2013-3-29 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午4:29:08
 * 版本号： v1.0
*/

package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.NewServerReservationDao;
import com.sz7road.web.model.newServerReservation.NewServerReservation;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-29 下午4:29:08
 * 版本号： v1.0
*/
public class NewServerReservationDaoImpl implements NewServerReservationDao {

    private static final String QUERY_COUNT_BY_USERNAME_AND_VERIFY = "SELECT COUNT(1) AS count FROM sa_new_server_reservation "
            + " WHERE user_name = ? AND verify = ?";

    private static final String INSERT_NEWSERVERRESERVATION = "INSERT INTO sa_new_server_reservation(user_name, verify_code, "
            + " phone_num, verify, activation_code1, activation_code2, create_date, ip_address, request_times) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String QUERY_NEWSERVERRESERVATION_BY_USERNAME_AND_VERIFYCODE = "SELECT * FROM sa_new_server_reservation "
            + " WHERE user_name = ? AND verify_code = ?";

    private static final String UPDATE_NEWSERVERRESERVATION = "UPDATE sa_new_server_reservation SET verify =?, activation_code1 =?, "
            + " activation_code2 = ?, verify_code = ?, phone_num = ?, ip_address = ?, create_date = ?, request_times = ? WHERE id = ?";

    private static final String QUERY_NEWSERVERRESERVATION_BY_USERNAME = "SELECT * FROM sa_new_server_reservation WHERE user_name = ? ";

    private static final String QUERY_EVERYDAY_REVERSION_COUNT = "SELECT COUNT(1) AS count FROM sa_new_server_reservation "
            + " WHERE substring(create_date, 1, 10) = ?";
    
    private static final String QUERY_NEWSERVERRESERVATION_BY_PHONENUM = "SELECT * FROM sa_new_server_reservation WHERE phone_num = ? ";

    private static NewServerReservationDaoImpl _instance = new NewServerReservationDaoImpl();

    private NewServerReservationDaoImpl() {
    }

    public static NewServerReservationDaoImpl getInstance() {
        return _instance;
    }

    @Override
    public int queryUserHasReserveation(final String userName) throws SQLException {
        final int[] count = new int[1];
        DB.select(QUERY_COUNT_BY_USERNAME_AND_VERIFY, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, userName);
                stmt.setBoolean(2, true);
            }
        });
        return count[0];
    }

    @Override
    public boolean addNewServerReservation(final NewServerReservation reservation) throws SQLException {
        boolean result = DB.insertUpdate(INSERT_NEWSERVERRESERVATION, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, reservation.getUserName());
                stmt.setString(2, reservation.getVerifyCode());
                stmt.setString(3, reservation.getPhoneNum());
                stmt.setBoolean(4, reservation.getVerify());
                stmt.setString(5, reservation.getActivationCode1());
                stmt.setString(6, reservation.getActivationCode2());
                stmt.setTimestamp(7, DateUtil.getCurrentTimestamp());
                stmt.setString(8, reservation.getIpAddress());
                stmt.setInt(9, reservation.getRequestTimes());
                stmt.executeUpdate();
            }
        });
        return result;
    }

    @Override
    public NewServerReservation queryNewServerReservationByUserNameAndVerifyCode(final String userName,
            final String verifyCode) throws SQLException {
        final NewServerReservation reservation = new NewServerReservation();
        DB.select(QUERY_NEWSERVERRESERVATION_BY_USERNAME_AND_VERIFYCODE, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    reservation.setId(rs.getInt("id"));
                    reservation.setActivationCode1(rs.getString("activation_code1"));
                    reservation.setActivationCode2(rs.getString("activation_code2"));
                    reservation.setPhoneNum(rs.getString("phone_num"));
                    reservation.setUserName(rs.getString("user_name"));
                    reservation.setVerifyCode(rs.getString("verify_code"));
                    reservation.setVerify(rs.getBoolean("verify"));
                    reservation.setCreateDate(rs.getTimestamp("create_date"));
                    reservation.setIpAddress(rs.getString("ip_address"));
                    reservation.setRequestTimes(rs.getInt("request_times"));
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, userName);
                stmt.setString(2, verifyCode);
            }
        });
        return reservation;
    }

    @Override
    public boolean updateNewServerReservation(final NewServerReservation reservation) throws SQLException {
        boolean result = DB.insertUpdate(UPDATE_NEWSERVERRESERVATION, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setBoolean(1, reservation.getVerify());
                stmt.setString(2, reservation.getActivationCode1());
                stmt.setString(3, reservation.getActivationCode2());
                //stmt.setString(4, reservation.getUserName());
                stmt.setString(4, reservation.getVerifyCode());
                stmt.setString(5, reservation.getPhoneNum());
                stmt.setString(6, reservation.getIpAddress());
                stmt.setTimestamp(7, DateUtil.getCurrentTimestamp());
                stmt.setInt(8, reservation.getRequestTimes());
                stmt.setInt(9, reservation.getId());
                stmt.executeUpdate();
            }
        });
        return result;
    }

    @Override
    public List<NewServerReservation> queryNewServerReservationByUserName(final String userName) throws SQLException {
        final List<NewServerReservation> list = new ArrayList<NewServerReservation>();
        DB.select(QUERY_NEWSERVERRESERVATION_BY_USERNAME, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                NewServerReservation reservation = null;
                while (rs.next()) {
                    reservation = new NewServerReservation();
                    reservation.setId(rs.getInt("id"));
                    reservation.setActivationCode1(rs.getString("activation_code1"));
                    reservation.setActivationCode2(rs.getString("activation_code2"));
                    reservation.setPhoneNum(rs.getString("phone_num"));
                    reservation.setUserName(rs.getString("user_name"));
                    reservation.setVerifyCode(rs.getString("verify_code"));
                    reservation.setVerify(rs.getBoolean("verify"));
                    reservation.setCreateDate(rs.getTimestamp("create_date"));
                    reservation.setIpAddress(rs.getString("ip_address"));
                    reservation.setRequestTimes(rs.getInt("request_times"));
                    list.add(reservation);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, userName);
            }
        });
        return list;
    }

    @Override
    public NewServerReservation queryNewServerReservationByUserNameLastest(final String userName) throws SQLException {
        StringBuilder builder = new StringBuilder(QUERY_NEWSERVERRESERVATION_BY_USERNAME);
        builder.append(" order by create_date desc limit 1");
        final NewServerReservation reservation = new NewServerReservation();
        DB.select(builder.toString(), new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    reservation.setId(rs.getInt("id"));
                    reservation.setActivationCode1(rs.getString("activation_code1"));
                    reservation.setActivationCode2(rs.getString("activation_code2"));
                    reservation.setPhoneNum(rs.getString("phone_num"));
                    reservation.setUserName(rs.getString("user_name"));
                    reservation.setVerifyCode(rs.getString("verify_code"));
                    reservation.setVerify(rs.getBoolean("verify"));
                    reservation.setCreateDate(rs.getTimestamp("create_date"));
                    reservation.setIpAddress(rs.getString("ip_address"));
                    reservation.setRequestTimes(rs.getInt("request_times"));
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, userName);
            }
        });
        return reservation;
    }

    @Override
    public int everyDayReservationCount() throws SQLException {
        final String newDateStr = DateUtil.format(new Date(), "yyyy-MM-dd");
        final int[] count = new int[1];
        DB.select(QUERY_EVERYDAY_REVERSION_COUNT, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, newDateStr);
            }
        });
        return count[0];
    }

    @Override
    public NewServerReservation queryUserNewsServerReserveationByPhoneNum(final String phoneNum) throws SQLException {
        final NewServerReservation reservation = new NewServerReservation();
        DB.select(QUERY_NEWSERVERRESERVATION_BY_PHONENUM, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    reservation.setId(rs.getInt("id"));
                    reservation.setActivationCode1(rs.getString("activation_code1"));
                    reservation.setActivationCode2(rs.getString("activation_code2"));
                    reservation.setPhoneNum(rs.getString("phone_num"));
                    reservation.setUserName(rs.getString("user_name"));
                    reservation.setVerifyCode(rs.getString("verify_code"));
                    reservation.setVerify(rs.getBoolean("verify"));
                    reservation.setCreateDate(rs.getTimestamp("create_date"));
                    reservation.setIpAddress(rs.getString("ip_address"));
                    reservation.setRequestTimes(rs.getInt("request_times"));
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, phoneNum);
            }
        });
        return reservation;
    }

}
