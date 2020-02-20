package com.example.android.e7gzlykora.viewmodels;


import com.example.android.e7gzlykora.model.BookingsRepository;
import com.example.android.e7gzlykora.model.Owner;
import com.example.android.e7gzlykora.model.OwnerRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

public class ProspectOwnerListViewModel extends ViewModel {

    private OwnerRepository ownerRepository;
    private BookingsRepository bookingsRepository;
    private MediatorLiveData<ArrayList<Owner>> ownerResponse = new MediatorLiveData<>();
    private MediatorLiveData<String> bookingsResponse = new MediatorLiveData<>();

    public ProspectOwnerListViewModel() {
        ownerRepository = OwnerRepository.getOwnerRepository();
        bookingsRepository = BookingsRepository.getBookingsRepository();
    }

    public LiveData<ArrayList<Owner>> getOwnersResponse() {
        return ownerResponse;
    }

    public void getOwnersLists() {
        ownersList();
    }

    public LiveData<String> getbookingsResponse() {
        return bookingsResponse;
    }

    public void getbookingsApi(int position, ArrayList<Owner> ownerList) {
        bookingsList(position, ownerList);
    }

    public LiveData<Integer> getPosition() {
        return bookingsRepository.getPositon();
    }

    private void bookingsList(int position, ArrayList<Owner> ownerList) {
        final LiveData<String> bookingsResponseApi = bookingsRepository.Book(position, ownerList);
        bookingsResponse.addSource(bookingsResponseApi, response -> {
            if (response != null) {
                bookingsResponse.setValue(response);
                bookingsResponse.removeSource(bookingsResponseApi);
            } else {
                bookingsResponse.removeSource(bookingsResponseApi);
            }
        });
    }

    private void ownersList() {
        final LiveData<ArrayList<Owner>> ownerResponseApi = ownerRepository.GetListData();
        ownerResponse.addSource(ownerResponseApi, response -> {
            if (response != null) {
                ownerResponse.setValue(response);
                ownerResponse.removeSource(ownerResponseApi);
            } else {
                ownerResponse.removeSource(ownerResponseApi);
            }
        });

    }


}
