
import org.naturalcli.IParameterType


"""
  The class implements a float parameter type.
  
  @see java.lang.Float#valueOf(String)
  @author Ferran Busquets
 
"""
class FloatParamType (IParameterType) :

    def getParameterTypeName(self) :
        return "float"

    def validateParameter(self, value) :
        try :
            float(value)
            return True
        except ValueError : 
            return false

    def validationMessage(self, value) :
        if validateParameter(value) :
        	return None
        else : 
        	return "Bad float."

    def convertParameterValue(self, strRepresentation) :
        return int(strRepresentation)
