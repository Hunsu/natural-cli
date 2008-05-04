
import org.naturalcli.IParameterType


"""
  The class implements a binary number parameter type.
  
  @see java.lang.Integer#valueOf(String, int)
  @author Ferran Busquets
 
"""
class BinaryParamType (IParameterType) :

    def getParameterTypeName(self) :
        return "binary"

    def validateParameter(self, value) :
        try :
            int (value, 2)
            return True
        except ValueError : 
            return False

    def validationMessage(self, value) :
        if validateParameter(value) :
        	return None
        else : 
        	return "Bad binary."

    def convertParameterValue(self, strRepresentation) :
        return int(strRepresentation, 2)
