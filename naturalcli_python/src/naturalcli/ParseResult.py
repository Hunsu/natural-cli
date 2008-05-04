
"""
  Encapsulate the data that restuls of a command parse. 
  It means the parameters values and the list of tokens found.
  
  @author Ferran Busquets
"""
class ParseResult :
		
	parameterValues=[]	
	tokensGiven=[]

	def __init__(self, parameterValues, tokensGiven) :
		"""
		  Constructor
		  
		  @param paramValues the ordered parameter values array. For optional
		                     parameters not provided will be <code>None</code>
		  @param tokensGiven the ordered array with all the tokens saying if
		                     each token is given or not. For non-optional tokens
	                         the value will be always <code>true</code>.
		"""
		if parameterValues == None :
			raise IllegalArgumentError, "Parameter values array cannot be None."
		if tokensGiven == None :
			raise IllegalArgumentError, "Tokens found array cannot be None."
		self.parameterValues = parameterValues
		self.tokensGiven = tokensGiven

	def getParameterValue(self, parameterIndex) :
		"""
		  Get the parameter value in the given index.
		  
		  @param parameterIndex the parameter index. This index is relative
		                        only for the parameters.
		  @return the parameter value.
		"""
		return self.parameterValues[parameterIndex]

	def getParameterValues(self) :
		"""
		  Get a copy of the parameter values
		  
		  @return object array with the prameter values 
		"""
		aux=[]
		aux.extend(self.parameterValues)
		return aux

	def getParameterCount(self) :
		"""
		  Get the number of all possible parameters
		   
		  @return number of parameters
		"""
		return len(self.parameterValues)

	def isTokenGiven(self, tokenIndex) :
		"""
		  Get if the token is given or not.
		  
		  @param tokenIndex the token index. 
		  
		  @return <code>true</code> if the token is given or <code>false</code>
		          if the token is not given (only for optional parameters).
		"""
		return self.tokensGiven[tokenIndex]

	def getTokensGiven(self) :
		"""
		  Get a copy of the tokens given.
		  
		  @return boolean array with the tokens given.
		"""
		aux=[]
		aux.extend(self.tokensGiven)		
		return aux
