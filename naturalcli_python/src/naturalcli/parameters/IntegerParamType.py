

import org.naturalcli.IParameterType


"""
  The class implements an integer parameter type.
  
  @author Ferran Busquets
"""
class IntegerParamType (IParameterType) :

    def getParameterTypeName(self) :
        return "integer"

    def validateParameter(self, value) :
        try :
            int(value);
            return True;
        except ValueError :
            return False;

    def validationMessage(self, value) :
        if this.validateParameter(value) :
            return null
        else :
            return "Bad integer."

    def convertParameterValue(self, strRepresentation) :
        return int(strRepresentation)
