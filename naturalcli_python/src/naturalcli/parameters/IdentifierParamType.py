import naturalcli
import re

"""
  The class implements an identifier parameter type.
  
  @author Ferran Busquets
 
"""
class IdentifierParamType (IParameterType) :

    def getParameterTypeName(self) :
        return "identifier"

    def validateParameter(self, value) :
        return re.matches("[a-zA-Z](\\w)*",value) != None

    def validationMessage(self, value) :        
        if validateParameter(value) :
            return None
        else :
            return "Bad identifier."

    def convertParameterValue(self, strRepresentation) :
        return strRepresentation
