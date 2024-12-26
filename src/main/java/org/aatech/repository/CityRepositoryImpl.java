package org.aatech.repository;

import org.aatech.model.CityModel;

public class CityRepositoryImpl extends DBSTATE implements CityRepository{
	@Override
	public boolean isAddNewCity(CityModel model) {
		try {
			cstmt = conn.prepareCall("call saveCity(?,?,?)");
			cstmt.setString(1, model.getCityName());
			cstmt.setInt(2, model.getStateId());
			cstmt.setInt(3, model.getId());
			boolean b = cstmt.execute();
			return !b;

		} catch (Exception ex) {
			System.out.println("Error is " + ex);

			return false;
		}
	}

	@Override
	public int getCityIdByName(String cityName, int stateId, int distId) {
		try {
			stmt=conn.prepareStatement("select cm.cityid from citymaster cm inner join citydistjoin cdj on cdj.cityid=cm.cityid where cm.cityname=? and cdj.stateid=? and cdj.distid=?");
			stmt.setString(1, cityName);
			stmt.setInt(2, stateId);
			stmt.setInt(3, distId);
			rs=stmt.executeQuery();
			if(rs.next())
			{
				return rs.getInt(1);
			}
			else
			{
				return 0;
			}
		} catch (Exception ex) {
			System.out.println("Error is "+ex);
			return 0;
		}
	}

	
}
