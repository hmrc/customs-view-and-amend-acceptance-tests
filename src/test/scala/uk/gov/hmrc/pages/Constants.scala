/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.pages

object Constants {

  enum UserType(val resource: String) {
    case AnalysisReworkClaim extends UserType("AnalysisReworkClaim")
    case ApprovedClaim extends UserType("ApprovedClaim")
    case NoClaims extends UserType("noClaims")
    case NotSubscribed extends UserType("notsubscribed")
    case OpenAnalysisClaim extends UserType("openAnalysisClaim")
    case OpenClaim extends UserType("openClaim")
    case OpenReworkClaim extends UserType("OpenReworkClaim")
    case Pagination extends UserType("pagination")
    case PausedClaim extends UserType("PausedClaim")
    case PendingApprovalClaim extends UserType("PendingApprovalClaim")
    case PendingComplianceCheckClaim extends UserType("PendingComplianceCheckClaim")
    case PendingComplianceCheckQueryClaim extends UserType("PendingComplianceCheckQueryClaim")
    case PendingComplianceRecommendationClaim extends UserType("PendingComplianceRecommendationClaim")
    case PendingDecisionLetterClaim extends UserType("PendingDecisionLetterClaim")
    case PendingPaymentConfirmationClaim extends UserType("PendingPaymentConfirmationClaim")
    case PendingQueriedClaim extends UserType("PendingQueriedClaim")
    case PendingRTBHClaim extends UserType("PendingRTBHClaim")
    case Registered extends UserType("registered")
    case RejectedFailedValidationClaim extends UserType("RejectedFailedValidationClaim")
    case ReplyToRTBHClaim extends UserType("ReplyToRTBHClaim")
    case ResolvedApprovedClaim extends UserType("ResolvedApprovedClaim")
    case ResolvedNoReplyClaim extends UserType("ResolvedNoReplyClaim")
    case ResolvedPartialRefusedClaim extends UserType("ResolvedPartialRefusedClaim")
    case ResolvedRefusedClaim extends UserType("ResolvedRefusedClaim")
    case ResolvedRejectedClaim extends UserType("ResolvedRejectedClaim")
    case ResolvedWithdrawnClaim extends UserType("ResolvedWithdrawnClaim")
    case ReworkPaymentDetailsClaim extends UserType("ReworkPaymentDetailsClaim")
    case RTBHsentClaim extends UserType("RTBHsentClaim")
    case SlowEoriHistoryEndpoint extends UserType("slowEoriHistoryEndpoint")
    case TPI01Error extends UserType("TPI01error")
    case UndeliverableEmail extends UserType("undeliverableEmail")
    case UnverifiedUser extends UserType("unverifiedUser")
  }

  enum BannerElement(val tagName: String) {
    case Links extends BannerElement("a")
    case Buttons extends BannerElement("button")
  }

  enum StaticTextKind {
    case Static, StaticContent, Hint, Label
  }

  enum PaginationContentKind(val tagName: String) {
    case Content extends PaginationContentKind("li")
    case Links extends PaginationContentKind("a")
  }

  enum FileType(val label: String) {
    case BillOfLading extends FileType("Bill of lading")
    case CommercialInvoice extends FileType("Commercial invoice")
    case PackingList extends FileType("Packing list")
    case SubstituteOrDiversionEntry extends FileType("Substitute or diversion entry")
  }

  enum YesNo(val label: String) {
    case Yes extends YesNo("Yes")
    case No extends YesNo("No")
  }

  enum CookieButton(val label: String) {
    case AcceptAdditional extends CookieButton("Accept additional cookies")
    case RejectAdditional extends CookieButton("Reject additional cookies")
    case HideMessage extends CookieButton("Hide cookies message")
  }

}
