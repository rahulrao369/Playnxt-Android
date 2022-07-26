package com.cw.playnxt.server;


import com.cw.playnxt.model.AboutUs.AboutUsResponse;
import com.cw.playnxt.model.AddBacklogList.AddBacklogListParaRes;
import com.cw.playnxt.model.AddFriendGame.AddFriendGameParaRes;
import com.cw.playnxt.model.AddFriendGame.AddFriendGameResponse;
import com.cw.playnxt.model.AddGameNote.AddGameNoteParaRes;
import com.cw.playnxt.model.AddGameStatus.AddGameStatusParaRes;
import com.cw.playnxt.model.AddWishlist.AddWishlistParaRes;
import com.cw.playnxt.model.CalenderDataModel.AddEvent.AddEventParaRes;
import com.cw.playnxt.model.CalenderDataModel.GetEvent.GetEventParaRes;
import com.cw.playnxt.model.CalenderDataModel.GetEvent.GetEventResponse;
import com.cw.playnxt.model.ChangePassword.ChangePasswordParaRes;
import com.cw.playnxt.model.ChatList.ChatListResponse;
import com.cw.playnxt.model.CheckPlan.CheckPlanResponse;
import com.cw.playnxt.model.CommunityData.GetCommunityListResponse;
import com.cw.playnxt.model.ContactUs.ContactUsResponse;
import com.cw.playnxt.model.DeleteBacklogList.DeleteBacklogListParaRes;
import com.cw.playnxt.model.DeleteCalenderEvent.DeleteCalenderEventParaRes;
import com.cw.playnxt.model.DeleteGame.DeleteGameParaRes;
import com.cw.playnxt.model.DeleteGameNote.DeleteGameNoteParaRes;
import com.cw.playnxt.model.EditBacklogList.EditBacklogListParaRes;
import com.cw.playnxt.model.EditCalenderEvent.EditCalenderEventParaRes;
import com.cw.playnxt.model.EditGameNote.EditGameNoteParaRes;
import com.cw.playnxt.model.EditWishlist.EditWishlistParaRes;
import com.cw.playnxt.model.FollowFriend.FollowFriendParaRes;
import com.cw.playnxt.model.FollowFriend.FollowFriendResponse;
import com.cw.playnxt.model.ForgotPassword.ForgotPasswordParaRes;
import com.cw.playnxt.model.GameInformation.GetGameInformationParaRes;
import com.cw.playnxt.model.GameInformation.GetGameInformationResponse;
import com.cw.playnxt.model.GetBacklogList.GetMyBacklogListResponse;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryBacklogListNameParaRes;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryBacklogListNameResponse;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryWishListNameParaRes;
import com.cw.playnxt.model.GetCategoryListName.GetCategoryWishListNameResponse;
import com.cw.playnxt.model.GetGameNote.GetGameNoteParaRes;
import com.cw.playnxt.model.GetGameNote.GetGameNoteResponse;
import com.cw.playnxt.model.GetMyFriendList.GetMyFriendListResponse;
import com.cw.playnxt.model.GetMyFriendProfile.GetMyFriendProfileParaRes;
import com.cw.playnxt.model.GetMyFriendProfile.GetMyFriendProfileResponse;
import com.cw.playnxt.model.GetMyProfile.GetMyProfileResponse;
import com.cw.playnxt.model.GetRecentGame.GetRecentGameResponse;
import com.cw.playnxt.model.GetWishlist.GetMyWishlistResponse;
import com.cw.playnxt.model.HomeButton.HomeButtonResponse;
import com.cw.playnxt.model.HomeData.HomeApiResponse;
import com.cw.playnxt.model.HomeSearch.SearchParaRes;
import com.cw.playnxt.model.HomeSearch.SearchResponse;
import com.cw.playnxt.model.LikeAndUnlikeProfile.LikeAndUnlikeProfileParaRes;
import com.cw.playnxt.model.LoginSignup.LoginParaRes;
import com.cw.playnxt.model.LoginSignup.LoginResponse;
import com.cw.playnxt.model.LoginSignup.SignupParaRes;
import com.cw.playnxt.model.LoginSignup.SignupResponse;
import com.cw.playnxt.model.MyActivePlan.MyActivePlanResponse;
import com.cw.playnxt.model.PurchaseFreePlan.PurchaseFreePlanParaRes;
import com.cw.playnxt.model.PurchaseFreePlan.PurchaseFreePlanResponse;
import com.cw.playnxt.model.PurchasePlan.PurchasePlanParaRes;
import com.cw.playnxt.model.PurchasePlan.PurchasePlanResponse;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.model.StaffPicks.StaffPicksResponse;
import com.cw.playnxt.model.SubscriptionPlan.SubscriptionPlanResponse;
import com.cw.playnxt.model.SuggestionData.SuggestionParaRes;
import com.cw.playnxt.model.SuggestionData.SuggestionResponse;
import com.cw.playnxt.model.UnfollowFriend.UnfollowFriendParaRes;
import com.cw.playnxt.model.ViewMyBacklogGame.ViewMyBacklogGameParaRes;
import com.cw.playnxt.model.ViewMyBacklogGame.ViewMyBacklogGameResponse;
import com.cw.playnxt.model.ViewMyWishlistGame.ViewMyWishlistGameParaRes;
import com.cw.playnxt.model.ViewMyWishlistGame.ViewMyWishlistGameResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface JsonPlaceHolderApi {
    @POST("register")
    Call<SignupResponse> registrationAPI(@Body SignupParaRes signupParaRes);

    @POST("login")
    Call<LoginResponse> loginAPI(@Header("Content-Type") String content_type,
                                 @Body LoginParaRes loginParaRes);

    @POST("users/add-event")
    Call<ResponseSatusMessage> AddEventAPI(@Header("Content-Type") String content_type,
                                           @Header("Authorization") String token,
                                           @Body AddEventParaRes addEventParaRes);

    @POST("users/get-event")
    Call<GetEventResponse> GetEventAPI(@Header("Content-Type") String content_type,
                                       @Header("Authorization") String token,
                                       @Body GetEventParaRes getEventParaRes);

    @POST("users/get-my-profile")
    Call<GetMyProfileResponse> GetMyProfileAPI(@Header("Content-Type") String content_type,
                                               @Header("Authorization") String token);

    @Multipart
    @POST("users/edit-profile")
    Call<ResponseSatusMessage> EditProfileAPI(@Header("Authorization") String token,
                                              @PartMap Map<String, RequestBody> data,
                                              @Part MultipartBody.Part image);

    @Multipart
    @POST("users/add-game")
    Call<ResponseSatusMessage> AddGameAPI(@Header("Authorization") String token,
                                          @PartMap Map<String, RequestBody> data,
                                          @Part MultipartBody.Part image);

    @POST("users/add-backloglist")
    Call<ResponseSatusMessage> AddBacklogListAPI(@Header("Content-Type") String content_type,
                                                 @Header("Authorization") String token,
                                                 @Body AddBacklogListParaRes addBacklogListParaRes);

    @POST("users/add-wishlist")
    Call<ResponseSatusMessage> AddWishlistAPI(@Header("Content-Type") String content_type,
                                              @Header("Authorization") String token,
                                              @Body AddWishlistParaRes addWishlistParaRes);

    @POST("users/get-my-backloglist")
    Call<GetMyBacklogListResponse> GetBacklogListAPI(@Header("Content-Type") String content_type,
                                                     @Header("Authorization") String token);

    @POST("users/get-my-wishlist")
    Call<GetMyWishlistResponse> GetWishlistAPI(@Header("Content-Type") String content_type,
                                               @Header("Authorization") String token);

    @POST("users/edit-backloglist")
    Call<ResponseSatusMessage> EditBacklogListAPI(@Header("Content-Type") String content_type,
                                                  @Header("Authorization") String token,
                                                  @Body EditBacklogListParaRes editBacklogListParaRes);

    @POST("users/edit-wishlist")
    Call<ResponseSatusMessage> EditWishlistAPI(@Header("Content-Type") String content_type,
                                               @Header("Authorization") String token,
                                               @Body EditWishlistParaRes editWishlistParaRes);

    @POST("users/change-pass")
    Call<ResponseSatusMessage> ChangePasswordAPI(@Header("Content-Type") String content_type,
                                                 @Header("Authorization") String token,
                                                 @Body ChangePasswordParaRes changePasswordParaRes);

    @POST("users/category-name")
    Call<GetCategoryBacklogListNameResponse> GetCategoryBacklogListNameAPI(@Header("Content-Type") String content_type,
                                                                           @Header("Authorization") String token,
                                                                           @Body GetCategoryBacklogListNameParaRes getCategoryBacklogListNameParaRes);

    @POST("users/category-name")
    Call<GetCategoryWishListNameResponse> GetCategoryWishListNameAPI(@Header("Content-Type") String content_type,
                                                                     @Header("Authorization") String token,
                                                                     @Body GetCategoryWishListNameParaRes getCategoryWishListNameParaRes);

    @POST("users/view-my-BackLoggame")
    Call<ViewMyBacklogGameResponse> ViewMyBacklogGameAPI(@Header("Content-Type") String content_type,
                                                         @Header("Authorization") String token,
                                                         @Body ViewMyBacklogGameParaRes viewMyBacklogGameParaRes);

    @POST("users/view-my-Wishlistgame")
    Call<ViewMyWishlistGameResponse> ViewMyWishlistGameAPI(@Header("Content-Type") String content_type,
                                                           @Header("Authorization") String token,
                                                           @Body ViewMyWishlistGameParaRes viewMyWishlistGameParaRes);

    @POST("users/add-note")
    Call<ResponseSatusMessage> AddGameNoteAPI(@Header("Content-Type") String content_type,
                                              @Header("Authorization") String token,
                                              @Body AddGameNoteParaRes addGameNoteParaRes);

    @POST("users/get-gamenote")
    Call<GetGameNoteResponse> GetGameNoteAPI(@Header("Content-Type") String content_type,
                                             @Header("Authorization") String token,
                                             @Body GetGameNoteParaRes getGameNoteParaRes);

    @POST("users/delete-list")
    Call<ResponseSatusMessage> DeleteBacklogListAPI(@Header("Content-Type") String content_type,
                                                    @Header("Authorization") String token,
                                                    @Body DeleteBacklogListParaRes deleteBacklogListParaRes);

    @POST("users/add-game-status")
    Call<ResponseSatusMessage> AddGameStatusAPI(@Header("Content-Type") String content_type,
                                                @Header("Authorization") String token,
                                                @Body AddGameStatusParaRes addGameStatusParaRes);

    @POST("users/game-info")
    Call<GetGameInformationResponse> GetGameInformationAPI(@Header("Content-Type") String content_type,
                                                           @Header("Authorization") String token,
                                                           @Body GetGameInformationParaRes getGameInformationParaRes);

    @POST("users/edit-game-note")
    Call<ResponseSatusMessage> EditGameNoteAPI(@Header("Content-Type") String content_type,
                                               @Header("Authorization") String token,
                                               @Body EditGameNoteParaRes editGameNoteParaRes);

    @POST("users/delete-game-note")
    Call<ResponseSatusMessage> DeleteGameNoteAPI(@Header("Content-Type") String content_type,
                                                 @Header("Authorization") String token,
                                                 @Body DeleteGameNoteParaRes deleteGameNoteParaRes);

    @POST("users/delete-game")
    Call<ResponseSatusMessage> DeleteGameAPI(@Header("Content-Type") String content_type,
                                             @Header("Authorization") String token,
                                             @Body DeleteGameParaRes deleteGameParaRes);

    @POST("users/get-my-friendList")
    Call<GetMyFriendListResponse> GetMyFriendListAPI(@Header("Content-Type") String content_type,
                                                     @Header("Authorization") String token);

    @POST("users/community")
    Call<GetCommunityListResponse> GetCommunityListAPI(@Header("Content-Type") String content_type,
                                                       @Header("Authorization") String token);

    @POST("users/like-and-unlike")
    Call<ResponseSatusMessage> LikeAndUnlikeProfileAPI(@Header("Content-Type") String content_type,
                                                       @Header("Authorization") String token,
                                                       @Body LikeAndUnlikeProfileParaRes likeAndUnlikeProfileParaRes);

    @POST("users/follow-friend")
    Call<FollowFriendResponse> FollowFriendAPI(@Header("Content-Type") String content_type,
                                               @Header("Authorization") String token,
                                               @Body FollowFriendParaRes followFriendParaRes);

    @POST("users/unfollow-friend")
    Call<ResponseSatusMessage> UnfollowFriendAPI(@Header("Content-Type") String content_type,
                                                 @Header("Authorization") String token,
                                                 @Body UnfollowFriendParaRes unfollowFriendParaRes);

    @POST("users/get-friend-profile")
    Call<GetMyFriendProfileResponse> GetMyFriendProfileAPI(@Header("Content-Type") String content_type,
                                                           @Header("Authorization") String token,
                                                           @Body GetMyFriendProfileParaRes getMyFriendProfileParaRes);

    @POST("users/edit-event")
    Call<ResponseSatusMessage> EditCalenderEventAPI(@Header("Content-Type") String content_type,
                                                    @Header("Authorization") String token,
                                                    @Body EditCalenderEventParaRes editCalenderEventParaRes);

    @POST("users/delete-event")
    Call<ResponseSatusMessage> DeleteCalenderEventAPI(@Header("Content-Type") String content_type,
                                                      @Header("Authorization") String token,
                                                      @Body DeleteCalenderEventParaRes deleteCalenderEventParaRes);

    @POST("users/recent-game")
    Call<GetRecentGameResponse> GetRecentGameAPI(@Header("Content-Type") String content_type,
                                                 @Header("Authorization") String token);

    @POST("users/logout")
    Call<ResponseSatusMessage> LogoutAPI(@Header("Content-Type") String content_type,
                                         @Header("Authorization") String token);

    @POST("users/home")
    Call<HomeApiResponse> HomeAPI(@Header("Content-Type") String content_type,
                                  @Header("Authorization") String token);

    @POST("users/delete-account")
    Call<ResponseSatusMessage> DeleteAccountAPI(@Header("Content-Type") String content_type,
                                                @Header("Authorization") String token);

    @POST("users/about-app")
    Call<AboutUsResponse> AboutUsAPI(@Header("Content-Type") String content_type,
                                     @Header("Authorization") String token);

    @POST("users/contact-us")
    Call<ContactUsResponse> ContactUsAPI(@Header("Content-Type") String content_type,
                                         @Header("Authorization") String token);

    @POST("users/suggest-new-feature")
    Call<SuggestionResponse> SuggestionAPI(@Header("Content-Type") String content_type,
                                           @Header("Authorization") String token,
                                           @Body SuggestionParaRes suggestionParaRes);

    @POST("users/search")
    Call<SearchResponse> SearchAPI(@Header("Content-Type") String content_type,
                                   @Header("Authorization") String token,
                                   @Body SearchParaRes searchParaRes);

    @POST("users/homeButton")
    Call<HomeButtonResponse> HomeButtonAPI(@Header("Content-Type") String content_type,
                                           @Header("Authorization") String token);

    @POST("users/staff-picks")
    Call<StaffPicksResponse> StaffPicksAPI(@Header("Content-Type") String content_type,
                                           @Header("Authorization") String token);

    @POST("users/add-friend-game")
    Call<AddFriendGameResponse> AddFriendGameAPI(@Header("Content-Type") String content_type,
                                                 @Header("Authorization") String token,
                                                 @Body AddFriendGameParaRes addFriendGameParaRes);

    @POST("users/forget-password")
    Call<ResponseSatusMessage> ForgotPasswordAPI(@Header("Content-Type") String content_type,
                                                 @Header("Authorization") String token,
                                                 @Body ForgotPasswordParaRes forgotPasswordParaRes);

    @POST("users/plans")
    Call<SubscriptionPlanResponse> SubscriptionPlanAPI(@Header("Content-Type") String content_type,
                                                       @Header("Authorization") String token);

    @POST("users/chatlist")
    Call<ChatListResponse> ChatListAPI(@Header("Content-Type") String content_type,
                                       @Header("Authorization") String token);

    @POST("users/purchase-plan")
    Call<PurchasePlanResponse> PurchasePlanAPI(@Header("Content-Type") String content_type,
                                               @Header("Authorization") String token,
                                               @Body PurchasePlanParaRes purchasePlanParaRes);

    @POST("users/myactiveplan")
    Call<MyActivePlanResponse> MyActivePlanAPI(@Header("Content-Type") String content_type,
                                               @Header("Authorization") String token);

    @POST("users/checksubscription")
    Call<CheckPlanResponse> CheckPlanAPI(@Header("Content-Type") String content_type,
                                         @Header("Authorization") String token);

    @POST("users/free-plan")
    Call<PurchaseFreePlanResponse> PurchaseFreePlanAPI(@Header("Content-Type") String content_type,
                                                       @Header("Authorization") String token,
                                                       @Body PurchaseFreePlanParaRes purchaseFreePlanParaRes);

}
