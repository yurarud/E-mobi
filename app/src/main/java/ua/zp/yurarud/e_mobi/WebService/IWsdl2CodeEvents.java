package ua.zp.yurarud.e_mobi.WebService;

/**
 * Created by Админ on 09.08.2017.
 */

public interface IWsdl2CodeEvents {
    public void Wsdl2CodeStartedRequest();
    public void Wsdl2CodeFinished(String methodName,Object Data);
    public void Wsdl2CodeFinishedWithException(Exception ex);
    public void Wsdl2CodeEndedRequest();
}
