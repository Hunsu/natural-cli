
import org.naturalcli.IParameterType;


"""
  The class implements an octal number parameter type.
  
  @see java.lang.Byte#valueOf(String, int)
  @author Ferran Busquets
 
"""
class OctalParamType (IParameterType) :

    def getParameterTypeName() :
        return "octal"

    def validateParameter(self, value) :
        try :
            int(value, 8);
            return True;
        except ValueError :
            return False

    def validationMessage(self, value) :
        if this.validateParameter(value) :
            return None
        else : 
            return "Bad octal."

    def convertParameterValue(self, strRepresentation) :
        return int(strRepresentation, 8)
