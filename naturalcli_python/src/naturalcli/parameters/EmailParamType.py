import re
import naturalcli

"""
  The class implements an email parameter type.
  
  @author Ferran Busquets
 
"""
class EmailParamType (IParameterType) :

	def getParameterTypeName(self) : 
		return "email"

	def validateParameter(self, value) :
		return re.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", value) != None

	def validationMessage(self, value) :
		if validateParameter(value) :
			return None
		else :
		    return "Bad email."

	def convertParameterValue(self, strRepresentation) : 
		return strRepresentation;
