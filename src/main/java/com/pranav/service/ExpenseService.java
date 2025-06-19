package com.pranav.service;

import com.pranav.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
	List<Expense> getAllUserExpenses(Long userId);

	List<Expense> getExpenseByDay(String date, Long userId);
	List<Expense> getExpenseByCategoryAndMonth(String category, String month, Long userId);
	List<String> getAllExpenseCategories(Long userId);
	Optional<Expense> getExpenseById(long id, Long userId);
	Expense addExpense(Expense expense, Long userId);
	boolean updateExpense(Expense expense, Long userId);
	boolean deleteExpense(Long id, Long userId);
}
