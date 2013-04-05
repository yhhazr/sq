<div id="playerrank">
	<div class="rankingNav">
	    <span>排名</span>
	    <span class="w90 role">角色名</span>
	    <span>等级</span>
	</div>
	<div class="rankingList">
	    <ul>
	    	<#list playerGradeRank.infoList as playerGrade>
	    	<#if playerGrade_index <3 >
		        <li class="topRank">		        
	        <#else>
	        	<li>
	        </#if>
					<em>${playerGrade_index+1}</em>
		            <i>${playerGrade.nickName}</i>
		            <span>${playerGrade.grades}</span>
	        	</li>
	        </#list>
	    </ul>
	</div>
</div>

<div id="playerfightPower" style="display:none;">
	<div class="rankingNav">
	    <span>排名</span>
	    <span class="w90 role">角色名</span>
	    <span>战斗力</span>
	</div>
	<div class="rankingList">
	    <ul>
	    	<#list playerFightRankPage.infoList as playerFightPower>
	    	<#if playerFightPower_index <3 >
		        <li class="topRank">		        
	        <#else>
	        	<li>
	        </#if>
					<em>${playerFightPower_index+1}</em>
		            <i>${playerFightPower.nickName}</i>
		            <span>${playerFightPower.fightCapacity}</span>
	        	</li>
	        </#list>
	    </ul>
	</div>
</div>

<div id="guildGrade" style="display:none;">
	<div class="rankingNav">
	    <span>排名</span>
	    <span class="w90 role">工会名</span>
	    <span>等级</span>
	</div>
	<div class="rankingList">
	    <ul>
	    	<#list guildGradeRank.infoList as guildGrade>
	    	<#if guildGrade_index <3 >
		        <li class="topRank">		        
	        <#else>
	        	<li>
	        </#if>
					<em>${guildGrade_index+1}</em>
		            <i>${guildGrade.consortiaName}</i>
		            <span>${guildGrade.levels}</span>
	        	</li>
	        </#list>
	    </ul>
	</div>
</div>

<div id="guildfightPower" style="display:none;">
	<div class="rankingNav">
	    <span>排名</span>
	    <span class="w90 role">工会名</span>
	    <span>战斗力</span>
	</div>
	<div class="rankingList">
	    <ul>
	    	<#list constainFightRankPage.infoList as guildFightPower>
	    	<#if guildFightPower_index <3 >
		        <li class="topRank">		        
	        <#else>
	        	<li>
	        </#if>
					<em>${guildFightPower_index+1}</em>
		            <i>${guildFightPower.consortiaName}</i>
		            <span>${guildFightPower.fightPower}</span>
	        	</li>
	        </#list>
	    </ul>
	</div>
</div>					