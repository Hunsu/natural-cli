import naturalcli

"""
  The class implements an string parameter type.
  
  @author Ferran Busquets
 
"""
class StringParamType (IParameterType) :

	def getParameterTypeName(self) :
		return "string"

	def validateParameter(self, value) :
		# Any string is ok, then all it's ok
		return True

	def validationMessage(self, value) :
		return None

	def convertParameterValue(self, strRepresentation) :
		return strRepresentation
