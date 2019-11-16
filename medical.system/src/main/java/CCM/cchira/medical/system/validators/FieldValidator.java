package CCM.cchira.medical.system.validators;

public interface FieldValidator<T> {
    boolean validate(T t);
}
