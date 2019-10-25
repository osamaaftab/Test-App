package com.osamaaftab.filtering;

import com.osamaaftab.filtering.Interactor.UserListInteractor;
import com.osamaaftab.filtering.contractor.UserListContractor;
import com.osamaaftab.filtering.repository.remote.UserServices;
import com.osamaaftab.filtering.ui.model.FilterModel;
import com.osamaaftab.filtering.ui.model.UserCity;
import com.osamaaftab.filtering.ui.model.UserData;
import com.osamaaftab.filtering.ui.model.UserModel;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InteractorTest {

    UserListInteractor userListInteractor;

    @Mock
    UserServices userServices;

    @Mock
    FilterModel filterModel;

    @Mock
    CompositeDisposable compositeDisposable;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Scheduler.Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };
        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
        userListInteractor = Mockito.spy(new UserListInteractor(userServices, filterModel, compositeDisposable));
    }

    @Test
    public void onFetchInitialUserListSuccess() throws Exception {

        ArrayList<UserData> userData = new ArrayList<>();
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        UserModel userModel = new UserModel(userData);

        when(userServices.getUserList())
                .thenReturn(Single.just(userModel));
        UserListContractor.IIntractor.OnResponse callBack = mock(UserListContractor.IIntractor.OnResponse.class);
        userListInteractor.getInitialUserListData(callBack);
        Mockito.verify(callBack, times(1)).onInitSuccess(userModel.getMatches());
        Mockito.verify(callBack, never()).onNoInternet();
    }

    @Test
    public void onFetchInitialUserListFailure() throws Exception {
        Exception exception = new Exception();

        ArrayList<UserData> userData = new ArrayList<>();
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        UserModel userModel = new UserModel(userData);

        when(userServices.getUserList())
                .thenReturn(Single.<UserModel>error(exception));

        UserListContractor.IIntractor.OnResponse callBack = mock(UserListContractor.IIntractor.OnResponse.class);
        userListInteractor.getInitialUserListData(callBack);
        Mockito.verify(callBack, never()).onInitSuccess(userModel.getMatches());
        Mockito.verify(callBack, times(1)).onNoInternet();
    }

    @Test
    public void onFetchFilteredUserListSuccess() throws Exception {

        ArrayList<UserData> userData = new ArrayList<>();
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        UserModel userModel = new UserModel(userData);
        when(userServices.getUserFilterList(filterModel))
                .thenReturn(Single.just(userModel));
        UserListContractor.IIntractor.OnResponse callBack = mock(UserListContractor.IIntractor.OnResponse.class);
        userListInteractor.getFilteredListDat(callBack, false, false, false, "1", "100", "18", "95", "60", "180", "50");
        Mockito.verify(callBack, times(1)).onRefreshDataSuccess(userModel.getMatches());
        Mockito.verify(callBack, never()).onNoInternet();
    }

    @Test
    public void onFetchFilteredUserListFailure() throws Exception {

        Exception exception = new Exception();

        ArrayList<UserData> userData = new ArrayList<>();
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        UserModel userModel = new UserModel(userData);

        when(userServices.getUserFilterList(filterModel))
                .thenReturn(Single.<UserModel>error(exception));

        UserListContractor.IIntractor.OnResponse callBack = mock(UserListContractor.IIntractor.OnResponse.class);
        userListInteractor.getFilteredListDat(callBack, false, false, false, "1", "100", "1", "95", "60", "180", "40");
        Mockito.verify(callBack, never()).onRefreshDataSuccess(userModel.getMatches());
        Mockito.verify(callBack, times(1)).onNoInternet();
    }

    @Test
    public void onResetUserListSuccess() throws Exception {

        ArrayList<UserData> userData = new ArrayList<>();
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        UserModel userModel = new UserModel(userData);

        when(userServices.getUserList())
                .thenReturn(Single.just(userModel));
        UserListContractor.IIntractor.OnResponse callBack = mock(UserListContractor.IIntractor.OnResponse.class);
        userListInteractor.getResetUserListData(callBack);
        Mockito.verify(callBack, times(1)).onRefreshDataSuccess(userModel.getMatches());
        Mockito.verify(callBack, never()).onNoInternet();
    }

    @Test
    public void onResetUserListFailure() throws Exception {
        Exception exception = new Exception();

        ArrayList<UserData> userData = new ArrayList<>();
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        userData.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", null, null, null, null, null, null, null, null));
        UserModel userModel = new UserModel(userData);

        when(userServices.getUserList())
                .thenReturn(Single.<UserModel>error(exception));

        UserListContractor.IIntractor.OnResponse callBack = mock(UserListContractor.IIntractor.OnResponse.class);
        userListInteractor.getResetUserListData(callBack);
        Mockito.verify(callBack, never()).onRefreshDataSuccess(userModel.getMatches());
        Mockito.verify(callBack, times(1)).onNoInternet();
    }

    //Assuming we get the list of users who's main_photo field is not null when HasPhoto is enabled from UI
    @Test
    public void onFilteredDataWithHasPhotoEnabled() throws Exception {

        ArrayList<UserData> epectedResponse = new ArrayList<>();
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        UserModel userModelExpected = new UserModel(epectedResponse);
        for (int i = 0; i < epectedResponse.size(); i++) {
            assertNotNull(userModelExpected.getMatches().get(i).getMain_photo());
        }
    }

    //Assuming we get the list of users who's contact_exchange is > 0 when InContact is enabled from UI
    @Test
    public void onFilteredDataWithInContactEnabled() throws Exception {

        ArrayList<UserData> epectedResponse = new ArrayList<>();
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        UserModel userModelExpected = new UserModel(epectedResponse);
        for (int i = 0; i < epectedResponse.size(); i++) {
            assertNotEquals(userModelExpected.getMatches().get(i).getContacts_exchanged().toString(), String.valueOf(0));
        }
    }

    //Assuming we get the list of users who's favourite is true
    @Test
    public void onFilteredDataWithFavEnabled() throws Exception {

        ArrayList<UserData> epectedResponse = new ArrayList<>();
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        UserModel userModelExpected = new UserModel(epectedResponse);
        for (int i = 0; i < epectedResponse.size(); i++) {
            Assert.assertTrue(userModelExpected.getMatches().get(i).getFavourite());
        }
    }

    //Assuming the range is from 15-25
    @Test
    public void onFilteredDataWithAgeRange() throws Exception {

        ArrayList<UserData> epectedResponse = new ArrayList<>();
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 18, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 22, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        UserModel userModelExpected = new UserModel(epectedResponse);

        int rangeStart = 15;
        int rangeend = 25;

        for (int i = 0; i < epectedResponse.size(); i++) {
            assertThat(userModelExpected.getMatches().get(i).getAge(), allOf(greaterThanOrEqualTo(rangeStart), lessThanOrEqualTo(rangeend)));
        }
    }

    //Assuming the height range is from 150-175
    @Test
    public void onFilteredDataWithHeightRange() throws Exception {

        ArrayList<UserData> epectedResponse = new ArrayList<>();
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 18, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 163, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 22, "Corporate Lawyer", 152, 0.76, 2, true, "Atheist", null));
        UserModel userModelExpected = new UserModel(epectedResponse);

        int rangeStart = 150;
        int rangeend = 175;

        for (int i = 0; i < epectedResponse.size(); i++) {
            assertThat(userModelExpected.getMatches().get(i).getHeight_in_cm(), allOf(greaterThanOrEqualTo(rangeStart), lessThanOrEqualTo(rangeend)));
        }
    }

    //Assuming the probability score range is from 50%-80%
    @Test
    public void onFilteredDataWithScoreRange() throws Exception {

        ArrayList<UserData> epectedResponse = new ArrayList<>();
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 18, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.70, 2, true, "Atheist", null));
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 22, "Corporate Lawyer", 153, 0.63, 2, true, "Atheist", null));
        UserModel userModelExpected = new UserModel(epectedResponse);

        double rangeStart = 50 / 100.0;
        double rangeend = 80 / 100.0;

        for (int i = 0; i < epectedResponse.size(); i++) {
            assertThat(userModelExpected.getMatches().get(i).getCompatibility_score(), allOf(greaterThanOrEqualTo(rangeStart), lessThanOrEqualTo(rangeend)));
        }
    }

    @Test
    public void onFilteredDataWithDistance() throws Exception {

        ArrayList<UserData> epectedResponse = new ArrayList<>();
        epectedResponse.add(new UserData("Caroline", "http://thecatapi.com/api/images/get?format=src&type=gif", 18, "Corporate Lawyer", 153, 0.76, 2, true, "Atheist", new UserCity("Leeds", 53.801277, -1.548567)));
        epectedResponse.add(new UserData("Katherine", "http://thecatapi.com/api/images/get?format=src&type=gif", 19, "Corporate Lawyer", 153, 0.70, 2, true, "Atheist", new UserCity("London", 51.509865, -0.118092)));
        UserModel userModelExpected = new UserModel(epectedResponse);

        double radius = 160; // In Km
        double lat = 52.412811;
        double lon = -1.778197;

        for (int i = 0; i < epectedResponse.size(); i++) {
            assertThat(getDistanceFromLatLonInKm(userModelExpected.getMatches().get(i).getCity().getLat(), userModelExpected.getMatches().get(i).getCity().getLon(), lat, lon), lessThanOrEqualTo(radius));
        }
    }


    @Test
    public void onDistroy() throws Exception {
        userListInteractor.onDisposable();
        Mockito.verify(compositeDisposable, times(1)).clear();
    }

    private double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; // Distance in km
        return d;
    }

    private double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }
}
