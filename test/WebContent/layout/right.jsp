<%@ page contentType="text/html;charset=UTF-8" %>

 <div id="leftMenu" class="easyui-accordion" data-options="fit:true,border:false">
      <div title="系统管理" style="overflow:auto;">
          <ul class="s1">
              <li><a class="toTabs" href="javascript:void(0);" to="system/user.jsp">用户管理</a></li>
              <li><a class="toTabs" href="javascript:void(0);" to="system/role.jsp">角色管理</a></li>
          </ul>
      </div>
      <div title="学校管理" data-options="selected:true" style="overflow:auto;">
          <ul class="s1">
              <li><a class="toTabs" href="javascript:void(0);" to="school/student.jsp">学生管理</a></li>
              <li><a class="toTabs" href="javascript:void(0);" to="school/teacher.jsp">教师管理</a></li>
              <li><a class="toTabs" href="javascript:void(0);" to="school/school.jsp">学校管理</a></li>
          </ul>
      </div>
</div>