import naturalcli
import urllib

"""
  The class implements an URL which is validated parameter type. 
  
  @see java.net.URL
  @author Ferran Busquets 
"""
class WorkingURLParamType (IParameterType) :

    def getParameterTypeName(self) :
        return "working_url"

    def validateParameter(self, value) :
        try :
            urllib.urlretrieve(value)
            return True
        except IOError :
            return False

    def validationMessage(self, value) :
        if this.validateParameter(value) :
            return None
        else :
            return "Not working URL."

    def convertParameterValue(self, strRepresentation) :
        try :
            return urllib.urlretrieve(value)
        except IOError :
            return None
