package login;

public enum Option {

    Admin, Student;

    private Option(){}

    public static Option fromValue(String v){
        return valueOf(v);
    }
}
