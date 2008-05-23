import naturalcli
import urllib

"""
  The class implements an URL parameter type. 
  @author Ferran Busquets 
"""
class URLParamType (IParameterType) :

    def getParameterTypeName(self) :
        return "url"

    def validateParameter(self, value) :
        try :
            urllib.urlopen(value)
            return True
        except IOError :
            return False

    def validationMessage(self, value):
        if this.validateParameter(value) :
            return None
        else :
            return "Malformed URL."

    def convertParameterValue(self, strRepresentation) : 
        try :
            return urllib.urlopen(value)
        except IOError :
            return None
