package Metier.Exceptions;

public class CreationInvalideException extends RuntimeException {
    public CreationInvalideException(){
    }
    
    public CreationInvalideException(String message){
        super(message);
    }
}
