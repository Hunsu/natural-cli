import naturalcli

"""
  The class implements an hexadecimal number parameter type.
  
  @see java.lang.Integer#valueOf(String, int)
  @author Ferran Busquets
 
"""
class HexadecimalParamType (IParameterType) :

    def getParameterTypeName(self) :
        return "hexadecimal"

    def validateParameter(self, value) :
        try :
            int(value, 16)
            return True;
        except ValueError:
            return False

    def validationMessage(self, value) :
        if validateParameter(value) :
            return None 
        else : 
            return "Bad hexadecimal."

    def convertParameterValue(self, strRepresentation) :
        return int(strRepresentation, 16)
