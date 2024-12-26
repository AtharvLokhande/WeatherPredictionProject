package org.aatech.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aatech.model.StateModel;
import org.aatech.repository.DBSTATE;
import org.aatech.repository.StateRepository;
import org.aatech.model.DistModel;

public class StateRepositoryImpl extends DBSTATE implements StateRepository{
	List<StateModel> list = new ArrayList<StateModel>();
	List<DistModel> distList = new ArrayList<DistModel>();

	@Override
	public boolean isAddNewState(StateModel model) {
		try {
			stmt = conn.prepareStatement("insert into statemaster values('0',?)");
			stmt.setString(1, model.getStateName());
			int value = stmt.executeUpdate();
			return value > 0 ? true : false;

		} catch (Exception ex) {
			System.out.println("Error is" + ex);
			return false;
		}
	}

	@Override
	public boolean isAddNewState(String stateName, String distName) {
		try {
			cstmt = conn.prepareCall("{call saveDist(?,?)}");
			cstmt.setString(1, stateName);
			cstmt.setString(2, distName);

			boolean b = cstmt.execute();

			return !b;

		} catch (Exception ex) {
			System.out.println("Error is " + ex);

			return false;
		}
	}
	@Override
	public Optional<List<StateModel>> getAllStates() {
		// list is equal with table
		// single state object consider in table
		// single field of state object is consider as colum in database table
		try {

			stmt = conn.prepareStatement("Select *from statemaster");
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new StateModel(rs.getInt(1), rs.getString(2)));
			}
			return list.size() > 0 ? Optional.of(list) : null;

		} catch (Exception ex) {
			System.out.println("Error is" + ex);
			return null;
		}

	}
	@Override
	public List getDistListByName(String stateName) {
		
		try {
			
			stmt=conn.prepareStatement("select d.distname,d.distid from distmaster d inner join statedistjoin stj on d.distid=stj.distid inner join statemaster sm on sm.stateid=stj.stateid where sm.statename=?");
			
			stmt.setString(1, stateName);
			rs=stmt.executeQuery();
			while(rs.next()) {
				DistModel model = new DistModel(rs.getString(1),rs.getInt(2));
				this.distList.add(model);
			}
			if(this.distList.size()>0) {
				return distList;
			}
			else {
				 System.out.println("State Not Found Exception"+stateName);
				 return null;
			}
			} 
		catch (Exception ex) {
			System.out.println("Error is "+ex);
			return null;
		}
		
	}
	@Override
	public int getStateIdByName(String stateName) {
		try {

			stmt = conn.prepareStatement("select stateid from statemaster where statename=?");
			stmt.setString(1, stateName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			} else {
				return -1;
			}

		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return -1;
		}
	}
	@Override
	public int getDistIdByName(String distName) {
		try {
			stmt = conn.prepareStatement("select distid from distmaster where distname=?");
			stmt.setString(1, distName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			} else {
				return 0;
			}
			
		} catch (Exception ex) {
			return 0;
		}
		
	}

	@Override
	public boolean isAddWeatherData(int cityId) {
		try {
			FileReader fr = new FileReader("/home/ubuntu/Desktop/seattle-weather.csv");
			BufferedReader br = new BufferedReader(fr);
	        String line;
	        boolean isSuccess = true;

	        while ((line = br.readLine()) != null) {
	            // Assuming CSV format: date,precipitation,tempMax,tempMin,wind,weather
	            String[] data = line.split(",");

	            if (data.length != 6) {
	                System.out.println("Invalid data format: " + line);
	                continue;
	            }

	            // Assigning and parsing values from the CSV
	            String date = data[0].trim();
	            float precipitation = Float.parseFloat(data[1].trim());
	            float tempMax = Float.parseFloat(data[2].trim());
	            float tempMin = Float.parseFloat(data[3].trim());
	            float wind = Float.parseFloat(data[4].trim());
	            String weather = data[5].trim();

	            // Call the stored procedure
	            cstmt = conn.prepareCall("{call saveweather(?, ?, ?, ?, ?, ?, ?)}");
	            cstmt.setInt(1, cityId); // Pass the cityId as a parameter to the function
	            cstmt.setString(2, date);
	            cstmt.setFloat(3, precipitation);
	            cstmt.setFloat(4, tempMax);
	            cstmt.setFloat(5, tempMin);
	            cstmt.setFloat(6, wind);
	            cstmt.setString(7, weather);

	            boolean result = cstmt.execute();
	            if (result) {
	                System.out.println("Error inserting data for line: " + line);
	                isSuccess = false;
	            }
	            cstmt.close(); // Always close the CallableStatement
	        }

	        br.close(); // Close the BufferedReader
	        fr.close(); // Close the FileReader
	        return isSuccess;
			

		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return false;
		}
	}


}
