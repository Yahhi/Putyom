#pragma checksum "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "4f25c96b5c29151ab6be0b62a75ed385d9a7d204"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Works_Index), @"mvc.1.0.view", @"/Views/Works/Index.cshtml")]
[assembly:global::Microsoft.AspNetCore.Mvc.Razor.Compilation.RazorViewAttribute(@"/Views/Works/Index.cshtml", typeof(AspNetCore.Views_Works_Index))]
namespace AspNetCore
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Rendering;
    using Microsoft.AspNetCore.Mvc.ViewFeatures;
#line 1 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/_ViewImports.cshtml"
using HackathonWeb;

#line default
#line hidden
#line 2 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/_ViewImports.cshtml"
using HackathonWeb.Models;

#line default
#line hidden
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"4f25c96b5c29151ab6be0b62a75ed385d9a7d204", @"/Views/Works/Index.cshtml")]
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"877c1bf945176ee8f3810635253c20d56bd369d1", @"/Views/_ViewImports.cshtml")]
    public class Views_Works_Index : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<IEnumerable<HackathonWeb.Context.Models.Work>>
    {
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_0 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("asp-action", "Details", global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        #line hidden
        #pragma warning disable 0169
        private string __tagHelperStringValueBuffer;
        #pragma warning restore 0169
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperExecutionContext __tagHelperExecutionContext;
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperRunner __tagHelperRunner = new global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperRunner();
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager __backed__tagHelperScopeManager = null;
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager __tagHelperScopeManager
        {
            get
            {
                if (__backed__tagHelperScopeManager == null)
                {
                    __backed__tagHelperScopeManager = new global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager(StartTagHelperWritingScope, EndTagHelperWritingScope);
                }
                return __backed__tagHelperScopeManager;
            }
        }
        private global::Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper;
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            BeginContext(54, 2, true);
            WriteLiteral("\r\n");
            EndContext();
#line 3 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
  
    ViewData["Title"] = "Index";

#line default
#line hidden
            BeginContext(97, 325, true);
            WriteLiteral(@"
<h1>Работы</h1>

<table class=""table"">
    <thead>
        <tr>
            <th>
                Название
            </th>
            <th>
                Контрагент
            </th>
            <th>
                Статус
            </th>
            <th></th>
        </tr>
    </thead>
    <tbody>
");
            EndContext();
#line 25 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
 foreach (var item in Model) {

#line default
#line hidden
            BeginContext(454, 48, true);
            WriteLiteral("        <tr>\r\n            <td>\r\n                ");
            EndContext();
            BeginContext(503, 39, false);
#line 28 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
           Write(Html.DisplayFor(modelItem => item.Name));

#line default
#line hidden
            EndContext();
            BeginContext(542, 55, true);
            WriteLiteral("\r\n            </td>\r\n            <td>\r\n                ");
            EndContext();
            BeginContext(598, 50, false);
#line 31 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
           Write(Html.DisplayFor(modelItem => item.Contractor.Name));

#line default
#line hidden
            EndContext();
            BeginContext(648, 39, true);
            WriteLiteral("\r\n            </td>\r\n            <td>\r\n");
            EndContext();
#line 34 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
                 if (item.ActualStart == DateTime.MinValue) {

#line default
#line hidden
            BeginContext(750, 51, true);
            WriteLiteral("                    <span>Работы не начаты</span>\r\n");
            EndContext();
#line 36 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
                }
                else {
                    

#line default
#line hidden
#line 38 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
                     if ((item.ActualStart - item.PlannedStart).TotalDays > 2) {

#line default
#line hidden
            BeginContext(926, 80, true);
            WriteLiteral("                        <span style=\"color: red;\">Работы начаты с опозданием на ");
            EndContext();
            BeginContext(1008, 53, false);
#line 39 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
                                                                            Write((int)(item.ActualStart - item.PlannedStart).TotalDays);

#line default
#line hidden
            EndContext();
            BeginContext(1062, 14, true);
            WriteLiteral(" дней</span>\r\n");
            EndContext();
#line 40 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
                    }
                    else {

#line default
#line hidden
            BeginContext(1127, 81, true);
            WriteLiteral("                        <span style=\"color: green;\">Работы начаты в срок</span>\r\n");
            EndContext();
#line 43 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
                    }

#line default
#line hidden
#line 43 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
                     
                }

#line default
#line hidden
            BeginContext(1250, 55, true);
            WriteLiteral("\r\n            </td>\r\n            <td>\r\n                ");
            EndContext();
            BeginContext(1305, 58, false);
            __tagHelperExecutionContext = __tagHelperScopeManager.Begin("a", global::Microsoft.AspNetCore.Razor.TagHelpers.TagMode.StartTagAndEndTag, "4f25c96b5c29151ab6be0b62a75ed385d9a7d2047327", async() => {
                BeginContext(1353, 6, true);
                WriteLiteral("Детали");
                EndContext();
            }
            );
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper = CreateTagHelper<global::Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper>();
            __tagHelperExecutionContext.Add(__Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper);
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.Action = (string)__tagHelperAttribute_0.Value;
            __tagHelperExecutionContext.AddTagHelperAttribute(__tagHelperAttribute_0);
            if (__Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues == null)
            {
                throw new InvalidOperationException(InvalidTagHelperIndexerAssignment("asp-route-id", "Microsoft.AspNetCore.Mvc.TagHelpers.AnchorTagHelper", "RouteValues"));
            }
            BeginWriteTagHelperAttribute();
#line 48 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
                                          WriteLiteral(item.Id);

#line default
#line hidden
            __tagHelperStringValueBuffer = EndWriteTagHelperAttribute();
            __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues["id"] = __tagHelperStringValueBuffer;
            __tagHelperExecutionContext.AddTagHelperAttribute("asp-route-id", __Microsoft_AspNetCore_Mvc_TagHelpers_AnchorTagHelper.RouteValues["id"], global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
            await __tagHelperRunner.RunAsync(__tagHelperExecutionContext);
            if (!__tagHelperExecutionContext.Output.IsContentModified)
            {
                await __tagHelperExecutionContext.SetOutputContentAsync();
            }
            Write(__tagHelperExecutionContext.Output);
            __tagHelperExecutionContext = __tagHelperScopeManager.End();
            EndContext();
            BeginContext(1363, 36, true);
            WriteLiteral("\r\n            </td>\r\n        </tr>\r\n");
            EndContext();
#line 51 "/Users/victorleontyev/Projects/HackathonWeb/HackathonWeb/Views/Works/Index.cshtml"
}

#line default
#line hidden
            BeginContext(1402, 24, true);
            WriteLiteral("    </tbody>\r\n</table>\r\n");
            EndContext();
        }
        #pragma warning restore 1998
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.ViewFeatures.IModelExpressionProvider ModelExpressionProvider { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IUrlHelper Url { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IViewComponentHelper Component { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IJsonHelper Json { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IHtmlHelper<IEnumerable<HackathonWeb.Context.Models.Work>> Html { get; private set; }
    }
}
#pragma warning restore 1591