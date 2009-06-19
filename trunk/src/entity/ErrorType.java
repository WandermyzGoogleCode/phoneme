package entity;

public enum ErrorType implements MyError{
	NOT_LOGIN, NO_IDENTICAL_FIELD, EDIT_NULL_USER, ID_NOT_MATCHED,
	NOT_ONLINE, LOGIN_FAILED, ALREADY_ONLINE, TARGET_NOT_EXIST,
	REMOTE_ERROR, NOT_ADMIN, ILLEGAL_NULL, ILLEGAL_NEW_INSTANCE,
	ILLEGAL_SEARCH, IDENTICAL_CONFLICT, SQL_ERROR, SELF_LOOP, ADMIN_CANNOT_QUIT
}
