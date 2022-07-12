package com.cw.playnxt.Interface;

import com.cw.playnxt.model.CommunityData.CapsulCommunityData;
import com.cw.playnxt.model.GetMyFriendList.GetMyFriendListDataCapsul;

public interface ItemClickLikeUnlikeFriend {
    public void onItemClick(String type, GetMyFriendListDataCapsul data, String status);
}
