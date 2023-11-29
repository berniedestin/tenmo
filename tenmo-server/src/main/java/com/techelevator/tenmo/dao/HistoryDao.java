package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.History;

import java.util.List;

public interface HistoryDao {
    public History createHistory(History history);

    public History getHistoryById(int id);

    public List<History> getHistoryByUser(int userId);

    public History updateHistory(History history);

    public int deleteHistoryById(int id);

}
