
public class GroupCreated {

//Handler to accept request from API 
	public String handleRequest(Map<String, String> event, Context context) {

		String response = new String("200 OK");

		return response;
	}
	// Setting up the search query to get the targeted things

	private List<String> getTargetListForDynamic(awsIot, String fortest) throws JSONException {
		List<String> targets = new ArrayList<>();
           String loc="Frankfurt";
           
		
			// Query for Dynamic group
			
			String appendQuery = "";
			if( fortest != null && fortest.equalsIgnoreCase("true")) {
				appendQuery = "";
			} else {
				appendQuery = "connectivity.connected:true AND ";
			}
			 
				query = appendQuery + "attributes.Location:" + loc + "  AND  shadow.reported.vibration >100 ";

					String ThingGroupName = "HighVibration" ;
			CreateDynamicThingGroupRequest request = new CreateDynamicThingGroupRequest()
					.withThingGroupName(ThingGroupName).withQueryString(query);
			
			CreateDynamicThingGroupResult result = null;
			try {
				
				groupresult = createDynamicThingGroup( awsIot, request);
				
				String groupname = groupresult.getThingGroupName();
				requestfordescribe = new DescribeThingGroupRequest().withThingGroupName(groupname);
				groupresult = describeThingGroup( awsIot, requestfordescribe);
				status = groupresult.getStatus();
				
			} catch (InterruptedException e) {
				
				
				e.printStackTrace();
			}

			
			while (transfer) {
			
				groupresult = describeThingGroup( awsIot, requestfordescribe);
				if (groupresult.getStatus().equalsIgnoreCase("Active")) {
					transfer = false;
				}
			}
			
			targets.add(groupresult.getThingGroupArn());
		
		
		return targets;
	}

//Create Dynamic Thing Group
	private DescribeThingGroupResult createDynamicThingGroup(AWSIot awsIot, CreateDynamicThingGroupRequest request)
			throws InterruptedException, JSONException {
		DescribeThingGroupRequest describeThingGroupRequest = new DescribeThingGroupRequest();
		describeThingGroupRequest.setThingGroupName(request.getThingGroupName());
		DescribeThingGroupResult describeThingGroupResult = null;
		try {
			describeThingGroupResult = awsIot.describeThingGroup(describeThingGroupRequest);
		} catch (Exception ex) {

		}
		if (describeThingGroupResult != null && describeThingGroupResult.getStatus() != null
				&& describeThingGroupResult.getStatus().equalsIgnoreCase("ACTIVE")) {
			UpdateDynamicThingGroupRequest updateDynamicThingGroupRequest = new UpdateDynamicThingGroupRequest();
			updateDynamicThingGroupRequest.setThingGroupName(request.getThingGroupName());
			updateDynamicThingGroupRequest.setQueryString(request.getQueryString());
			updateDynamicThingGroupRequest.setIndexName(describeThingGroupResult.getIndexName());
			updateDynamicThingGroupRequest.setThingGroupProperties(describeThingGroupResult.getThingGroupProperties());
			UpdateDynamicThingGroupResult updateDynamicThingGroupResult = awsIot
					.updateDynamicThingGroup(updateDynamicThingGroupRequest);

		} else {
			CreateDynamicThingGroupResult result = awsIot.createDynamicThingGroup(request);

		}

		describeThingGroupResult = awsIot.describeThingGroup(describeThingGroupRequest);

		return describeThingGroupResult;
	}

//Check the status of the thing group
	private DescribeThingGroupResult describeThingGroup(AWSIot awsIot,
			DescribeThingGroupRequest describeThingGroupRequest) {
		DescribeThingGroupResult groupresult = awsIot.describeThingGroup(describeThingGroupRequest);

		return groupresult;
	}

//Verify the devices added to the thing group 
	private ListThingsInThingGroupResult listThingsInThingGroup(AWSIot awsIot,
			ListThingsInThingGroupRequest listThingsInThingGroupRequest) {
		ListThingsInThingGroupResult listresult = awsIot.listThingsInThingGroup(listThingsInThingGroupRequest);

		return listresult;
	}

}
