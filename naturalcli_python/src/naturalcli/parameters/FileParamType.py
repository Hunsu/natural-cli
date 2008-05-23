import naturalcli

"""
  The class implements a parameter type with an existing a file name.
  
  @author Ferran Busquets
"""
class FileParamType (IParameterType) : 

    def getParameterTypeName(self) :
        return "file"

    def validateParameter(self, value) :
        try :
            f = open(value)
            return True
        except IOError :
            return False

    def validationMessage(self, value) :
        if validateParameter(value) :
            return None
        else :
            return "File does not exists."

    def convertParameterValue(self, strRepresentation) :
        return open(strRepresentation)
