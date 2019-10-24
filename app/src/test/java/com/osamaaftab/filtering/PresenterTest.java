package com.osamaaftab.filtering;

import com.osamaaftab.filtering.Interactor.UserListInteractor;
import com.osamaaftab.filtering.contractor.UserListContractor;
import com.osamaaftab.filtering.presenter.UserListPresenter;
import com.osamaaftab.filtering.ui.model.UserData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.times;


public class PresenterTest {

    UserListPresenter presenter;

    @Mock
    private UserListInteractor loginInteractor;

    @Mock
    private UserListContractor.IView loginView;

    @Mock
    ArrayList<UserData> userModelList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = Mockito.spy(new UserListPresenter(loginView, loginInteractor));
    }

    @Test
    public void onCallInitList() throws Exception {
        presenter.onFetchUserList();
        Mockito.verify(loginView, times(1)).onCreateDialog();
        Mockito.verify(loginView, times(1)).onShowProgress();
        Mockito.verify(loginInteractor, times(1)).getInitialUserListData(presenter);
    }

    @Test
    public void onGetListSuccess() throws Exception {
        presenter.onInitSuccess(userModelList);
        Mockito.verify(loginView, times(1)).onHideProgress();
        Mockito.verify(loginView, times(1)).onsetUserListAdapter(userModelList);
    }

    @Test
    public void onNoInternet() throws Exception {
        presenter.onNoInternet();
        Mockito.verify(loginView, times(1)).onShowNoInternetError();
    }

    @Test
    public void onCallFilteredUserList() throws Exception {
        presenter.onApplyFilter(true, true, true, "1", "80", "18", "95", "120", "180", "upper bound");
        Mockito.verify(loginView, times(1)).onHideDialog();
        Mockito.verify(loginView, times(1)).onShowProgress();
        Mockito.verify(loginInteractor, times(1)).getFilteredListDat(presenter, true, true, true, "1", "80", "18", "95", "120", "180", "upper bound");
    }

    @Test
    public void onFilteredListSuccess() throws Exception {
        presenter.onRefreshDataSuccess(userModelList);
        Mockito.verify(loginView, times(1)).onHideProgress();
        Mockito.verify(loginView, times(1)).onUpdateUserListAdapter(userModelList);
    }

    @Test
    public void onAttemptFilterButton() throws Exception {
        presenter.onAttemptFilterButton();
        Mockito.verify(loginView, times(1)).onViewDialog();
    }

    @Test
    public void onAttemptResetFilterButton() throws Exception {
        presenter.onResetFilter();
        Mockito.verify(loginView, times(1)).onCreateDialog();
        Mockito.verify(loginView, times(1)).onHideDialog();
        Mockito.verify(loginView, times(1)).onShowProgress();
        Mockito.verify(loginInteractor, times(1)).getResetUserListData(presenter);
    }


    @Test
    public void onDistroy() throws Exception {
        presenter.onDistroy();
        Mockito.verify(loginInteractor, times(1)).onDisposable();
    }

}
